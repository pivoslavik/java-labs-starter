package edu.course.lab04;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class SampleRegistryTest {

  @Test
  void LogicallyEqualKeysFindSameValueInRegistry() {
    SampleRegistry registry = new SampleRegistry();
    SampleId id1 = new SampleId("MNIST", 101);
    SampleId id2 = new SampleId("MNIST", 101);
    DataSample sample = new DataSample("Image_Data_101");
    registry.put(id1, sample);
    assertEquals(sample, registry.get(id2));
    assertTrue(registry.containsKey(id2));
  }

  @Test
  void ReAddingEqualKeyReplacesValueAndDoesNotIncreaseSize() {
    SampleRegistry registry = new SampleRegistry();
    SampleId id1 = new SampleId("MNIST", 50);
    SampleId id2 = new SampleId("MNIST", 50);
    DataSample initialSample = new DataSample("Version_1");
    DataSample updatedSample = new DataSample("Version_2");
    registry.put(id1, initialSample);
    assertEquals(1, registry.size());
    registry.put(id2, updatedSample);
    assertEquals(1, registry.size());
    assertEquals(updatedSample, registry.get(id1));
  }

  @Test
  void UnequalKeysWithSameHashCodeStoreDifferentValues() {
    HashMap<SampleId, DataSample> map = new HashMap<>();
    SampleId id1 = new SampleId("a", 31);
    SampleId id2 = new SampleId("b", 0);
    DataSample sample1 = new DataSample("Data_A");
    DataSample sample2 = new DataSample("Data_B");
    map.put(id1, sample1);
    map.put(id2, sample2);
    assertEquals(id1.hashCode(), id2.hashCode());
    assertEquals(2, map.size());
    assertEquals(sample1, map.get(id1));
    assertEquals(sample2, map.get(id2));
    assertNotEquals(id1, id2);
  }

  @Test
  void EqualsReturnsFalseForNullAndOtherTypes() {
    SampleId id = new SampleId("MNIST", 1);
    assertFalse(id.equals(null));
    assertFalse(id.equals("MNIST"));
  }

//  @Test
//  void ModifyingMutableKeyBreaksLookupInHashMap() {
//    HashMap<SampleId, DataSample> map = new HashMap<>();
//    SampleId mutableKey = new SampleId("TrainSet", 10);
//    DataSample sample = new DataSample("Features");
//    map.put(mutableKey, sample);
//    assertTrue(map.containsKey(mutableKey));
//    mutableKey.setObservationIndex(20);
//    assertFalse(map.containsKey(mutableKey));
//    assertNull(map.get(mutableKey));
//  }
}
