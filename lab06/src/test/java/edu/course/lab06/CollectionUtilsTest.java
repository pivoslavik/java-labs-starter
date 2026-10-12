package edu.course.lab06;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CollectionUtilsTest {

  @Test
  void testCopyAllFromSubtypeToSupertype() {
    List<Integer> integers = List.of(1, 2, 3);
    List<Number> numbers = new ArrayList<>();
    CollectionUtils.copyAll(integers, numbers);
    assertEquals(3, numbers.size());
    assertEquals(1, numbers.get(0));
    assertEquals(2, numbers.get(1));
    assertEquals(3, numbers.get(2));
  }

  @Test
  void testCopyAllToObjectList() {
    List<Double> doubles = List.of(3.14, 2.71);
    List<Object> objects = new ArrayList<>();
    CollectionUtils.copyAll(doubles, objects);
    assertEquals(2, objects.size());
    assertEquals(3.14, objects.get(0));
    assertEquals(2.71, objects.get(1));
  }

  @Test
  void testCopyAllNullValidation() {
    List<Integer> validList = List.of(10);
    List<Number> targetList = new ArrayList<>();
    assertThrows(NullPointerException.class, () -> CollectionUtils.copyAll(null, targetList));
    assertThrows(NullPointerException.class, () -> CollectionUtils.copyAll(validList, null));
  }
}
