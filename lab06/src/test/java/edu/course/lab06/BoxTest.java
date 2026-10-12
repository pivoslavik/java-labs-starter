package edu.course.lab06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class BoxTest {

  @Test
  void testStringBox() {
    Box<String> stringBox = new Box<>("Hello");
    assertEquals("Hello", stringBox.getItem());

    stringBox.setItem("World");
    assertEquals("World", stringBox.getItem());
  }

  @Test
  void testIntegerBox() {
    Box<Integer> integerBox = new Box<>(10);
    assertEquals(10, integerBox.getItem());

    integerBox.setItem(25);
    assertEquals(25, integerBox.getItem());
  }
}
