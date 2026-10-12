package edu.course.lab06;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public final class Stack<T> {
  private final List<T> items;

  public Stack() {
    this.items = new ArrayList<>();
  }

  public void push(T value) {
    if (value == null) {
      throw new IllegalArgumentException("stack value mustn't be null");
    }
    items.add(value);
  }

  public T pop() {
    if (items.isEmpty()) {
      throw new NoSuchElementException("stack is empty");
    }
    return items.removeLast();
  }

  public T peek() {
    if (items.isEmpty()) {
      throw new NoSuchElementException("stack is empty");
    }
    return items.getLast();
  }

  public int size() {
    return items.size();
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }
}
