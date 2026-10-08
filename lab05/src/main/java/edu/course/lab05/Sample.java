package edu.course.lab05;

import java.util.Objects;

public class Sample {
  private final String id;
  private final String label;
  private final String source;
  private final int featureCount;
  private final SampleStatus status;

  public Sample(String id, String label, String source, int featureCount, SampleStatus status) {
    this.id = id;
    this.label = label;
    this.source = source;
    this.featureCount = featureCount;
    this.status = status;
  }

  public String getId() {
    return id;
  }

  public String getLabel() {
    return label;
  }

  public String getSource() {
    return source;
  }

  public int getFeatureCount() {
    return featureCount;
  }

  public SampleStatus getStatus() {
    return status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Sample sample = (Sample) o;
    return featureCount == sample.featureCount &&
        Objects.equals(id, sample.id) &&
        Objects.equals(label, sample.label) &&
        Objects.equals(source, sample.source) &&
        status == sample.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
