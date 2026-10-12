package edu.course.lab08;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BatchProcessorTest {

  record DatasetStats(double sum, double average) {}

  private final TaskFunction<List<Double>, DatasetStats> statsCalculator = chunk -> {
    if (chunk == null || chunk.isEmpty()) {
      throw new IllegalArgumentException("Dataset chunk cannot be empty");
    }
    double sum = 0.0;
    for (double num : chunk) {
      sum += num;
    }
    return new DatasetStats(sum, sum / chunk.size());
  };

  @Test
  void shouldHandleEmptyDataset() throws InterruptedException {
    BatchProcessor<List<Double>, DatasetStats> processor = new BatchProcessor<>();
    ExecutorService executor = Executors.newFixedThreadPool(2);

    try {
      List<TaskResult<DatasetStats>> seqResults = processor.processSequentially(List.of(), statsCalculator);
      List<TaskResult<DatasetStats>> concResults = processor.processConcurrently(List.of(), statsCalculator, executor);

      assertTrue(seqResults.isEmpty());
      assertTrue(concResults.isEmpty());
    } finally {
      executor.shutdown();
    }
  }

  @Test
  void shouldProcessMultipleChunksSuccessfully() throws InterruptedException {
    BatchProcessor<List<Double>, DatasetStats> processor = new BatchProcessor<>();
    ExecutorService executor = Executors.newFixedThreadPool(2);

    List<List<Double>> chunks = List.of(
        List.of(1.0, 2.0, 3.0),
        List.of(10.0, 20.0),
        List.of(5.0)
    );

    try {
      List<TaskResult<DatasetStats>> results = processor.processConcurrently(chunks, statsCalculator, executor);

      assertEquals(3, results.size());

      assertTrue(results.get(0).isSuccess());
      assertEquals(0, results.get(0).index());
      assertEquals(new DatasetStats(6.0, 2.0), results.get(0).value());

      assertTrue(results.get(1).isSuccess());
      assertEquals(1, results.get(1).index());
      assertEquals(new DatasetStats(30.0, 15.0), results.get(1).value());

      assertTrue(results.get(2).isSuccess());
      assertEquals(2, results.get(2).index());
      assertEquals(new DatasetStats(5.0, 5.0), results.get(2).value());
    } finally {
      executor.shutdown();
    }
  }

  @Test
  void shouldPreserveOrderWhenCompletionTimeDiffers() throws InterruptedException {
    BatchProcessor<Integer, Integer> processor = new BatchProcessor<>();
    ExecutorService executor = Executors.newFixedThreadPool(2);

    CountDownLatch firstTaskCanFinish = new CountDownLatch(1);

    TaskFunction<Integer, Integer> invertedFunction = input -> {
      if (input == 0) {
        firstTaskCanFinish.await();
        return 0;
      } else {
        firstTaskCanFinish.countDown();
        return input;
      }
    };

    try {
      List<TaskResult<Integer>> results = processor.processConcurrently(
          List.of(0, 1),
          invertedFunction,
          executor
      );

      assertEquals(2, results.size());
      assertEquals(0, results.get(0).index());
      assertEquals(0, results.get(0).value());
      assertEquals(1, results.get(1).index());
      assertEquals(1, results.get(1).value());
    } finally {
      executor.shutdown();
    }
  }

  @Test
  void shouldPreserveSuccessfulResultsWhenOneTaskFails() throws InterruptedException {
    BatchProcessor<List<Double>, DatasetStats> processor = new BatchProcessor<>();
    ExecutorService executor = Executors.newFixedThreadPool(2);

    List<List<Double>> chunks = List.of(
        List.of(2.0, 4.0),
        List.of(),
        List.of(10.0)
    );

    try {
      List<TaskResult<DatasetStats>> results = processor.processConcurrently(chunks, statsCalculator, executor);

      assertEquals(3, results.size());

      assertTrue(results.get(0).isSuccess());
      assertEquals(0, results.get(0).index());
      assertEquals(new DatasetStats(6.0, 3.0), results.get(0).value());

      assertFalse(results.get(1).isSuccess());
      assertEquals(1, results.get(1).index());
      assertInstanceOf(IllegalArgumentException.class, results.get(1).error());

      assertTrue(results.get(2).isSuccess());
      assertEquals(2, results.get(2).index());
      assertEquals(new DatasetStats(10.0, 10.0), results.get(2).value());
    } finally {
      executor.shutdown();
    }
  }

  @Test
  void shouldNotModifyInputList() throws InterruptedException {
    BatchProcessor<List<Double>, DatasetStats> processor = new BatchProcessor<>();
    ExecutorService executor = Executors.newFixedThreadPool(2);

    List<List<Double>> originalChunks = new ArrayList<>();
    originalChunks.add(List.of(1.0, 2.0));
    originalChunks.add(List.of(3.0, 4.0));

    List<List<Double>> snapshot = List.copyOf(originalChunks);

    try {
      processor.processSequentially(originalChunks, statsCalculator);
      assertEquals(snapshot, originalChunks);

      processor.processConcurrently(originalChunks, statsCalculator, executor);
      assertEquals(snapshot, originalChunks);
    } finally {
      executor.shutdown();
    }
  }

  @Test
  void shouldEnsureExecutorShutdownByCaller() throws InterruptedException {
    BatchProcessor<List<Double>, DatasetStats> processor = new BatchProcessor<>();
    ExecutorService executor = Executors.newFixedThreadPool(2);

    try {
      processor.processConcurrently(List.of(List.of(1.0)), statsCalculator, executor);
      assertFalse(executor.isShutdown());
    } finally {
      executor.shutdown();
    }

    assertTrue(executor.isShutdown());
  }

  @Test
  void shouldCancelFuturesWhenInterrupted() throws InterruptedException {
    BatchProcessor<Integer, Integer> processor = new BatchProcessor<>();
    ExecutorService executor = Executors.newFixedThreadPool(2);

    CountDownLatch taskStarted = new CountDownLatch(1);
    CountDownLatch blocker = new CountDownLatch(1);

    TaskFunction<Integer, Integer> hangingFunction = input -> {
      taskStarted.countDown();
      blocker.await();
      return input;
    };

    Thread workerThread = new Thread(() -> {
      try {
        processor.processConcurrently(List.of(1, 2), hangingFunction, executor);
      } catch (InterruptedException ignored) {
      }
    });

    try {
      workerThread.start();
      taskStarted.await();

      workerThread.interrupt();
      workerThread.join();
    } finally {
      blocker.countDown();
      executor.shutdown();
    }
  }
}