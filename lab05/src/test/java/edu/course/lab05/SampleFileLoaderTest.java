package edu.course.lab05;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SampleFileLoaderTest {

  @TempDir
  Path tempDir;

  private SampleFileLoader loader;

  @BeforeEach
  void setUp() {
    loader = new SampleFileLoader();
  }

  @Test
  void loadValidFile() throws IOException {
    Path file = tempDir.resolve("samples_valid.csv");
    List<String> content = List.of(
        "id;label;source;featureCount;status",
        "S-101;cat;camera-1;128;READY",
        "S-102;dog;lidar-2;64;IN_PROGRESS"
    );
    Files.write(file, content, StandardCharsets.UTF_8);
    SampleLoadResult result = loader.load(file);
    assertTrue(result.getErrors().isEmpty());
    assertEquals(2, result.getSamples().size());
    assertEquals(new Sample("S-101", "cat", "camera-1", 128, SampleStatus.READY), result.getSamples().get(0));
    assertEquals(new Sample("S-102", "dog", "lidar-2", 64, SampleStatus.IN_PROGRESS), result.getSamples().get(1));
  }

  @Test
  void loadFileWithUnicodeContent() throws IOException {
    Path file = tempDir.resolve("samples_unicode.csv");
    List<String> content = List.of(
        "id;label;source;featureCount;status",
        "S-103;кот;датчик-север;256;READY"
    );
    Files.write(file, content, StandardCharsets.UTF_8);
    SampleLoadResult result = loader.load(file);
    assertTrue(result.getErrors().isEmpty());
    assertEquals(1, result.getSamples().size());
    Sample sample = result.getSamples().getFirst();
    assertEquals("кот", sample.getLabel());
    assertEquals("датчик-север", sample.getSource());
  }

  @Test
  void loadFileWithEmptyField() throws IOException {
    Path file = tempDir.resolve("samples_empty_field.csv");
    List<String> content = List.of(
        "id;label;source;featureCount;status",
        "S-104;;sensor-1;128;READY"
    );
    Files.write(file, content, StandardCharsets.UTF_8);
    SampleLoadResult result = loader.load(file);
    assertTrue(result.getSamples().isEmpty());
    assertEquals(1, result.getErrors().size());
    ValidationError error = result.getErrors().getFirst();
    assertEquals(2, error.getLineNumber());
    assertEquals("label", error.getField());
  }

  @Test
  void loadFileWithInvalidNumber() throws IOException {
    Path file = tempDir.resolve("samples_invalid_number.csv");
    List<String> content = List.of(
        "id;label;source;featureCount;status",
        "S-105;cat;camera-1;NaN;READY",
        "S-106;dog;camera-2;0;READY"
    );
    Files.write(file, content, StandardCharsets.UTF_8);
    SampleLoadResult result = loader.load(file);
    assertTrue(result.getSamples().isEmpty());
    assertEquals(2, result.getErrors().size());
    assertEquals("featureCount", result.getErrors().getFirst().getField());
    assertEquals(2, result.getErrors().getFirst().getLineNumber());
    assertEquals("featureCount", result.getErrors().get(1).getField());
    assertEquals(3, result.getErrors().get(1).getLineNumber());
  }

  @Test
  void loadFileWithUnknownStatus() throws IOException {
    Path file = tempDir.resolve("samples_unknown_status.csv");
    List<String> content = List.of(
        "id;label;source;featureCount;status",
        "S-107;bird;drone-3;32;UNKNOWN_STATUS"
    );
    Files.write(file, content, StandardCharsets.UTF_8);
    SampleLoadResult result = loader.load(file);
    assertTrue(result.getSamples().isEmpty());
    assertEquals(1, result.getErrors().size());
    ValidationError error = result.getErrors().getFirst();
    assertEquals(2, error.getLineNumber());
    assertEquals("status", error.getField());
  }

  @Test
  void loadFileWithInvalidColumnCount() throws IOException {
    Path file = tempDir.resolve("samples_columns.csv");
    List<String> content = List.of(
        "id;label;source;featureCount;status",
        "S-108;car;camera",
        "S-109;bike;camera;16;READY;unexpected"
    );
    Files.write(file, content, StandardCharsets.UTF_8);
    SampleLoadResult result = loader.load(file);
    assertTrue(result.getSamples().isEmpty());
    assertEquals(2, result.getErrors().size());
    assertEquals("columns", result.getErrors().getFirst().getField());
    assertEquals(2, result.getErrors().getFirst().getLineNumber());
    assertEquals("columns", result.getErrors().get(1).getField());
    assertEquals(3, result.getErrors().get(1).getLineNumber());
  }

  @Test
  void loadFileWithMixedValidAndInvalidRows() throws IOException {
    Path file = tempDir.resolve("samples_mixed.csv");
    List<String> content = List.of(
        "id;label;source;featureCount;status",
        "S-110;valid_1;cam-1;128;READY",
        "S-111;invalid;cam-2;-5;READY",
        "S-112;valid_2;cam-3;256;DONE"
    );
    Files.write(file, content, StandardCharsets.UTF_8);
    SampleLoadResult result = loader.load(file);
    assertEquals(2, result.getSamples().size());
    assertEquals("S-110", result.getSamples().getFirst().getId());
    assertEquals("S-112", result.getSamples().get(1).getId());
    assertEquals(1, result.getErrors().size());
    assertEquals(3, result.getErrors().getFirst().getLineNumber());
    assertEquals("featureCount", result.getErrors().getFirst().getField());
  }

  @Test
  void loadMissingFileThrowsDataLoadingException() {
    Path nonExistent = tempDir.resolve("missing_samples.csv");
    DataLoadingException exception = assertThrows(
        DataLoadingException.class,
        () -> loader.load(nonExistent)
    );
    assertTrue(exception.getMessage().contains("Cannot read " + nonExistent));
    assertNotNull(exception.getCause());
    assertInstanceOf(IOException.class, exception.getCause());
  }
}
