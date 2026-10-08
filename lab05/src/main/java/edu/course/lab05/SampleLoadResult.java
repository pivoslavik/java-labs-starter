package edu.course.lab05;

import java.util.ArrayList;
import java.util.List;

public final class SampleLoadResult {
  private final List<Sample> samples;
  private final List<ValidationError> errors;

  public SampleLoadResult(List<Sample> samples, List<ValidationError> errors) {
    this.samples = new ArrayList<>(samples);
    this.errors = new ArrayList<>(errors);
  }

  public List<Sample> getSamples() {
    return samples;
  }

  public List<ValidationError> getErrors() {
    return errors;
  }
}
