package edu.course.lab08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TaskRunnerTest {

  @Test
  void shouldExecuteTasksAndCollectResultsInOrder() throws ExecutionException, InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(2);
    List<Future<Integer>> futures = new ArrayList<>();
    List<Integer> results = new ArrayList<>();

    try {
      Callable<Integer> task1 = () -> 10;
      Callable<Integer> task2 = () -> 20;
      Callable<Integer> task3 = () -> 30;

      futures.add(executor.submit(task1));
      futures.add(executor.submit(task2));
      futures.add(executor.submit(task3));

      for (Future<Integer> future : futures) {
        results.add(future.get());
      }

      assertEquals(List.of(10, 20, 30), results);
    }
    finally {
      executor.shutdown();
    }
  }
}
