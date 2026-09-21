package edu.course.lab04;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class BookTest {

  @Test
  void EqualsIsbnAddValueToEqualsBucketInHashMap() {
    HashMap<Book, Integer> booksCount = new HashMap<>();
    Book book1 = new Book("1", "123");
    Book book2 = new Book("1", "12332");
    Book book3 = new Book("3", "123");
    booksCount.putIfAbsent(book1, 0);
    booksCount.put(book1, booksCount.get(book1) + 1);
    booksCount.putIfAbsent(book2, 0);
    booksCount.put(book2, booksCount.get(book2) + 1);
    booksCount.putIfAbsent(book3, 0);
    booksCount.put(book3, booksCount.get(book3) + 1);
    assertEquals(2, booksCount.get(book1));
    assertEquals(2, booksCount.get(book2));
    assertEquals(1, booksCount.get(book3));
  }

  @Test
  void EqualsReturnTrueIfIsbnIsEqualsAndFalseIfNotEquals() {
    Book book1 = new Book("1", "123");
    Book book2 = new Book("1", "12332");
    Book book3 = new Book("3", "12323");
    assertEquals(book1, book2);
    assertNotEquals(book1, book3);
    assertNotEquals(book2, book3);
  }
}
