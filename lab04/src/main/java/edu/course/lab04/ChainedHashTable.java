package edu.course.lab04;

import java.util.Objects;

public class ChainedHashTable {
  private static final int DEFAULT_INIT_BUCKET_CAPACITY = 16;
  private static final float DEFAULT_LOAD_FACTOR = 0.75f;

  private Node[] table;
  private int size;

  private static class Node {
    final SampleId key;
    DataSample value;
    Node next;

    Node(SampleId key, DataSample value, Node next) {
      this.key = key;
      this.value = value;
      this.next = next;
    }
  }

  public ChainedHashTable() {
    this(DEFAULT_INIT_BUCKET_CAPACITY);
  }

  public ChainedHashTable(int buckets) {
    this.table = new Node[buckets];
  }

  public DataSample get(SampleId key) {
    if (key == null) {
      return null;
    }
    int index = getIndex(key, table.length);
    Node current = table[index];
    while (current != null) {
      if (Objects.equals(current.key, key)) {
        return current.value;
      }
      current = current.next;
    }
    return null;
  }

  public void put(SampleId key, DataSample value) {
    if (key == null) {
      throw new IllegalArgumentException("Key cannot be null");
    }
    if (size >= table.length * DEFAULT_LOAD_FACTOR) {
      resize();
    }
    int index = getIndex(key, table.length);
    Node current = table[index];
    while (current != null) {
      if (Objects.equals(current.key, key)) {
        current.value = value;
        return;
      }
      current = current.next;
    }
    table[index] = new Node(key, value, table[index]);
    size++;
  }

  public DataSample remove(SampleId key) {
    if (key == null) {
      return null;
    }
    int index = getIndex(key, table.length);
    Node current = table[index];
    Node prev = null;
    while (current != null) {
      if (Objects.equals(current.key, key)) {
        if (prev == null) {
          table[index] = current.next;
        } else {
          prev.next = current.next;
        }
        size--;
        return current.value;
      }
      prev = current;
      current = current.next;
    }
    return null;
  }

  public int size() {
    return size;
  }

  public boolean containsKey(SampleId key) {
    return get(key) != null;
  }

  private void resize() {
    int newCapacity = table.length * 2;
    Node[] newTable = new Node[newCapacity];
    for (Node head : table) {
      Node current = head;
      while (current != null) {
        Node next = current.next;
        int newIndex = getIndex(current.key, newCapacity);
        current.next = newTable[newIndex];
        newTable[newIndex] = current;
        current = next;
      }
    }
    table = newTable;
  }

  private int getIndex(SampleId key, int capacity) {
    int index = key.hashCode() % capacity;
    if (index < 0) {
      index += capacity;
    }
    return index;
  }
}
