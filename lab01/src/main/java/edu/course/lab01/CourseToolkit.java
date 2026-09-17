package edu.course.lab01;

/**
 * Небольшие методы для первой лабораторной работы.
 */
public final class CourseToolkit {

  private CourseToolkit() {
    // Утилитарный класс не должен иметь экземпляров.
  }

  /**
   * Возвращает true, если число четное.
   */
  public static boolean isEven(int number) {
    return number % 2 == 0;
  }

  public static boolean isPrime(int number) {
    if (number < 2) {
      return false;
    }
    for (int i = 2; i * i <= number; i++) {
      if (number % i == 0) {
        return false;
      }
    }
    return true;
  }

  public static boolean isPalindrome(String text) {
    if (text == null) {
      throw new IllegalArgumentException("text is null");
    }
    for (int i = 0; i < text.length() / 2; i++) {
      if (text.charAt(i) != text.charAt(text.length() - 1 - i)) {
        return false;
      }
    }
    return true;
  }

  public static double average(int[] values) {
    if (values == null || values.length == 0) {
      throw new IllegalArgumentException("array is null or empty");
    }
    long summa = 0;
    for (int val : values) {
      summa += val;
    }
    return (double) summa / values.length;
  }
}
