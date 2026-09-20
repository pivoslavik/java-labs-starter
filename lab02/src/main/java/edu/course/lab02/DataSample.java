package edu.course.lab02;

public class DataSample {
  private String id;
  private String label;
  private SampleStatus status;
  private double[] features;

  public DataSample(String id, String label, SampleStatus status, double[] features) {
    if (id == null || id.isBlank()) {
      throw new IllegalArgumentException("id is null or empty");
    }
    if (label == null || label.isBlank()) {
      throw new IllegalArgumentException("label is null or empty");
    }
    if (status == null) {
      throw new IllegalArgumentException("status is null");
    }
    if (features == null || features.length == 0) {
      throw new IllegalArgumentException("features is null or empty");
    }
    this.id = id;
    this.label = label;
    this.status = status;
    this.features = features.clone();
  }

  public void changeStatus(SampleStatus status) {
    if (status == null) {
      throw new IllegalArgumentException("new status is null");
    }
    this.status = status;
  }

  public boolean isCompleted() {
    return this.status == SampleStatus.COMPLETED;
  }

  public double average() {
    double summa = 0;
    for (double value : features) {
      summa += value;
    }
    return summa / features.length;
  }

  public double[] normalizeFeatures() {
    double min = features[0];
    double max = features[0];
    for (double val : features) {
      if (val < min) min = val;
      if (val > max) max = val;
    }
    double range = max - min;
    double[] normalized = new double[features.length];
    for (int i = 0; i < features.length; i++) {
      normalized[i] = (range == 0) ? 0.0 : (features[i] - min) / range;
    }
    return normalized;
  }

  public String getId() {
    return id;
  }

  public String getLabel() {
    return label;
  }

  public SampleStatus getStatus() {
    return status;
  }

  public double[] getFeatures() {
    return features.clone();
  }
}
