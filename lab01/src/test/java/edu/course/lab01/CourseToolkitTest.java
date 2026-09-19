package edu.course.lab01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CourseToolkitTest {
  final static double DELTA = 0.002;

  @Test
  void isEvenReturnsTrueForEvenNumber() {
    boolean result = CourseToolkit.isEven(8);

    assertTrue(result);
  }

  @Test
  void isEvenReturnsFalseForOddNumber() {
    boolean result = CourseToolkit.isEven(7);

    assertFalse(result);
  }

  @Test
  void isPrimeReturnsTrueForPrimeNumber() {
    assertTrue(CourseToolkit.isPrime(23));
    assertTrue(CourseToolkit.isPrime(17));
    assertTrue(CourseToolkit.isPrime(2));
  }

  @ParameterizedTest
  @ValueSource(ints = {23, 17, 2})
  void isPrimeReturnsTrueForPrimeNumberParameterizedTest(int value) {
    assertTrue(CourseToolkit.isPrime(value));
  }

  @Test
  void isPrimeReturnsFalseForNonPrimeNumber() {
    assertFalse(CourseToolkit.isPrime(16));
    assertFalse(CourseToolkit.isPrime(4));
    assertFalse(CourseToolkit.isPrime(20));
    assertFalse(CourseToolkit.isPrime(120));
  }

  @ParameterizedTest
  @ValueSource(ints = {16, 4, 20, 120})
  void isPrimeReturnsFalseForNonPrimeNumberParameterizedTest(int value) {
    assertFalse(CourseToolkit.isPrime(value));
  }

  @Test
  void isPrimeReturnsFalseForOne() {
    assertFalse(CourseToolkit.isPrime(1));
  }

  @Test
  void isPrimeReturnsFalseForZero() {
    assertFalse(CourseToolkit.isPrime(0));
  }

  @Test
  void isPalindromeReturnsTrueForPalindrome() {
    assertTrue(CourseToolkit.isPalindrome("121323121"));
    assertTrue(CourseToolkit.isPalindrome("Y d4d Y"));
    assertTrue(CourseToolkit.isPalindrome(""));
  }

  @Test
  void isPalindromeReturnsFalseForNonPalindrome() {
    assertFalse(CourseToolkit.isPalindrome("24222"));
    assertFalse(CourseToolkit.isPalindrome("ddewjej3w"));
  }

  @Test
  void isPalindromeThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> CourseToolkit.isPalindrome(null));
  }

  @Test
  void averageReturnsAverageOfArray() {
    assertEquals(2.5, CourseToolkit.average(new int[]{2, 3}), DELTA);
    assertEquals(2, CourseToolkit.average(new int[]{2, 2, 2, 2, 2}), DELTA);
    assertEquals(3, CourseToolkit.average(new int[]{5, 1}), DELTA);
    assertEquals(0, CourseToolkit.average(new int[]{0}), DELTA);
  }

  @Test
  void averageReturnsCorrectResultOfArrayOfNegative() {
    assertEquals(-2.0, CourseToolkit.average(new int[]{-1, -2, -3}), DELTA);
  }

  @Test
  void averageThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(null));
    assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(new int[0]));
  }
}
