package edu.course.lab07;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestStreamApi {
  @Test
  void testTransformations() {
    List<String> example = List.of("java", "", "git", "maven", "api");
    List<String> excepted = List.of("API", "GIT", "JAVA", "MAVEN");
    List<String> result = example.stream().filter(s -> !s.isBlank()).map(String::toUpperCase).sorted().toList();
    assertEquals(excepted, result);
  }
}
