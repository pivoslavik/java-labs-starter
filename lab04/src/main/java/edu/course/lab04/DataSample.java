package edu.course.lab04;
import java.util.Objects;

public class DataSample {
  private String payload;

  public DataSample(String payload) {
    this.payload = payload;
  }

  public String getPayload() {
    return payload;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataSample that = (DataSample) o;
    return Objects.equals(payload, that.payload);
  }

  @Override
  public int hashCode() {
    return Objects.hash(payload);
  }
}