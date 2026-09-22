package edu.course.lab04;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChainedHashTableTest {

  @Test
  void LogicallyEqualKeysFindSameValueInChainedHashTable() {
    ChainedHashTable table = new ChainedHashTable();
    SampleId id1 = new SampleId("MNIST", 101);
    SampleId id2 = new SampleId("MNIST", 101);
    DataSample sample = new DataSample("Image_Data_101");
    table.put(id1, sample);
    assertEquals(sample, table.get(id2));
    assertTrue(table.containsKey(id2));
  }

  @Test
  void ReAddingEqualKeyReplacesValueAndDoesNotIncreaseSize() {
    ChainedHashTable table = new ChainedHashTable();
    SampleId id1 = new SampleId("MNIST", 50);
    SampleId id2 = new SampleId("MNIST", 50);
    DataSample initialSample = new DataSample("Version_1");
    DataSample updatedSample = new DataSample("Version_2");
    table.put(id1, initialSample);
    assertEquals(1, table.size());
    table.put(id2, updatedSample);
    assertEquals(1, table.size());
    assertEquals(updatedSample, table.get(id1));
  }

  @Test
  void RemoveExistingKeyDecreasesSizeAndReturnsValue() {
    ChainedHashTable table = new ChainedHashTable();
    SampleId id = new SampleId("MNIST", 5);
    DataSample sample = new DataSample("Image_Data_101");
    table.put(id, sample);
    assertEquals(1, table.size());
    DataSample removed = table.remove(id);
    assertEquals(sample, removed);
    assertEquals(0, table.size());
    assertFalse(table.containsKey(id));
    assertNull(table.get(id));
  }

  @Test
  void AutomaticResizePreservesAllElements() {
    ChainedHashTable table = new ChainedHashTable(2);
    for (int i = 0; i < 10; i++) {
      table.put(new SampleId("Dataset", i), new DataSample("Data_" + i));
    }
    assertEquals(10, table.size());
    for (int i = 0; i < 10; i++) {
      assertEquals(new DataSample("Data_" + i), table.get(new SampleId("Dataset", i)));
    }
  }
}
