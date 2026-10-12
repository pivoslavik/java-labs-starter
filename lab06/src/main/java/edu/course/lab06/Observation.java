package edu.course.lab06;

public class Observation {
  private final long id;
  private final String status;
  private final String source;
  private final int featureCount;

  public Observation(long id, String status, String source, int featureCount) {
    this.id = id;
    this.status = status;
    this.source = source;
    this.featureCount = featureCount;
  }

  public long getId() {
    return id;
  }

  public String getStatus() {
    return status;
  }

  public String getSource() {
    return source;
  }

  public int getFeatureCount() {
    return featureCount;
  }
}