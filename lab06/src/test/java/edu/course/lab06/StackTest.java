package edu.course.lab06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {
  private Stack<String> stack;

  @BeforeEach
  void setUp() {
    stack = new Stack<>();
  }

  @Test
  void testEmptyStack() {
    assertTrue(stack.isEmpty());
    assertEquals(0, stack.size());
    assertThrows(NoSuchElementException.class, () -> stack.peek());
    assertThrows(NoSuchElementException.class, () -> stack.pop());
  }

  @Test
  void testPushAndPeek() {
    stack.push("Alpha");
    assertFalse(stack.isEmpty());
    assertEquals(1, stack.size());
    assertEquals("Alpha", stack.peek());

    stack.push("Beta");
    assertEquals(2, stack.size());
    assertEquals("Beta", stack.peek());
  }

  @Test
  void testPop() {
    stack.push("First");
    stack.push("Second");
    stack.push("Third");

    assertEquals("Third", stack.pop());
    assertEquals(2, stack.size());
    assertEquals("Second", stack.peek());

    assertEquals("Second", stack.pop());
    assertEquals("First", stack.pop());

    assertTrue(stack.isEmpty());
    assertEquals(0, stack.size());
    assertThrows(NoSuchElementException.class, () -> stack.pop());
  }

  @Test
  void testNullDisallowed() {
    assertThrows(Exception.class, () -> stack.push(null));
  }
}