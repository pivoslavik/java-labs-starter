package edu.course.lab08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Учебный каркас: оба метода реализует студент в ЛР 8.
 */
public final class BatchProcessor<T, R> {

  public List<TaskResult<R>> processSequentially(
      List<T> inputs,
      TaskFunction<T, R> function
  ) {
    if (inputs == null || inputs.isEmpty()) {
      return List.of();
    }

    List<TaskResult<R>> results = new ArrayList<>(inputs.size());
    int index = 0;
    for (T input : inputs) {
      try {
        R value = function.apply(input);
        results.add(TaskResult.success(index, value));
      } catch (Exception e) {
        results.add(TaskResult.failure(index, e));
      }
      index++;
    }
    return results;
  }

  public List<TaskResult<R>> processConcurrently(
      List<T> inputs,
      TaskFunction<T, R> function,
      ExecutorService executor) throws InterruptedException {
    if (inputs == null || inputs.isEmpty()) {
      return List.of();
    }

    List<Future<R>> futures = new ArrayList<>(inputs.size());
    for (T input : inputs) {
      Callable<R> task = () -> function.apply(input);
      futures.add(executor.submit(task));
    }

    List<TaskResult<R>> results = new ArrayList<>(inputs.size());
    try {
      for (int i = 0; i < futures.size(); i++) {
        try {
          R value = futures.get(i).get();
          results.add(TaskResult.success(i, value));
        } catch (ExecutionException e) {
          Throwable cause = e.getCause();
          Exception error = (cause instanceof Exception ex) ? ex : new RuntimeException(cause);
          results.add(TaskResult.failure(i, error));
        }
      }
    } catch (InterruptedException e) {
      for (Future<R> future : futures) {
        future.cancel(true);
      }
      throw e;
    }

    return results;
  }
}
