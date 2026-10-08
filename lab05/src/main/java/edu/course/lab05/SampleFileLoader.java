package edu.course.lab05;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class SampleFileLoader {
  private static final String EXPECTED_HEADER = "id;label;source;featureCount;status";
  private static final int EXPECTED_COLUMNS_COUNT = 5;

  public SampleLoadResult load(Path path) {
    List<String> lines;
    try {
      lines = Files.readAllLines(path, StandardCharsets.UTF_8);
    } catch (IOException cause) {
      throw new DataLoadingException("Cannot read " + path, cause);
    }
    List<Sample> samples = new ArrayList<>();
    List<ValidationError> errors = new ArrayList<>();
    if (lines.isEmpty()) {
      errors.add(new ValidationError(1, "header", "File is empty"));
      return new SampleLoadResult(samples, errors);
    }
    String actualHeader = lines.getFirst().trim();
    if (!EXPECTED_HEADER.equals(actualHeader)) {
      errors.add(new ValidationError(1, "header", "Invalid header: expected " + EXPECTED_HEADER));
    }
    for (int i = 1; i < lines.size(); i++) {
      int lineNumber = i + 1;
      String line = lines.get(i);

      if (line.trim().isEmpty()) {
        continue;
      }
      String[] fields = line.split(";", -1);
      if (fields.length != EXPECTED_COLUMNS_COUNT) {
        errors.add(new ValidationError(lineNumber, "columns", "Expected 5 columns, got " + fields.length));
        continue;
      }
      boolean rowHasErrors = false;
      String id = fields[0].trim();
      if (id.isEmpty()) {
        errors.add(new ValidationError(lineNumber, "id", "ID must not be empty"));
        rowHasErrors = true;
      }
      String label = fields[1].trim();
      if (label.isEmpty()) {
        errors.add(new ValidationError(lineNumber, "label", "Label must not be empty"));
        rowHasErrors = true;
      }
      String source = fields[2].trim();
      if (source.isEmpty()) {
        errors.add(new ValidationError(lineNumber, "source", "Source must not be empty"));
        rowHasErrors = true;
      }
      int featureCount = 0;
      String countStr = fields[3].trim();
      if (countStr.isEmpty()) {
        errors.add(new ValidationError(lineNumber, "featureCount", "Feature count must not be empty"));
        rowHasErrors = true;
      } else {
        try {
          featureCount = Integer.parseInt(countStr);
          if (featureCount <= 0) {
            errors.add(new ValidationError(lineNumber, "featureCount", "Feature count must be positive"));
            rowHasErrors = true;
          }
        } catch (NumberFormatException e) {
          errors.add(new ValidationError(lineNumber, "featureCount", "Invalid number format: " + countStr));
          rowHasErrors = true;
        }
      }
      SampleStatus status = null;
      String statusStr = fields[4].trim();
      if (statusStr.isEmpty()) {
        errors.add(new ValidationError(lineNumber, "status", "Status must not be empty"));
        rowHasErrors = true;
      } else {
        try {
          status = SampleStatus.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
          errors.add(new ValidationError(lineNumber, "status", "Unknown status: " + statusStr));
          rowHasErrors = true;
        }
      }
      if (!rowHasErrors) {
        samples.add(new Sample(id, label, source, featureCount, status));
      }
    }
    return new SampleLoadResult(samples, errors);
  }
}
