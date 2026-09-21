package edu.course.lab04;

import java.util.Objects;

public final class SampleId {
  private final String datasetName;
  private final long observationIndex;

  public SampleId(String datasetName, long observationIndex) {
    this.datasetName = datasetName;
    this.observationIndex = observationIndex;
  }

  public String getDatasetName() {
    return datasetName;
  }

  public long getObservationIndex() {
    return observationIndex;
  }

//  public void setObservationIndex(int observationIndex) {
//    this.observationIndex = observationIndex;
//  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SampleId sampleId = (SampleId) o;
    return observationIndex == sampleId.observationIndex &&
        Objects.equals(datasetName, sampleId.datasetName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datasetName, observationIndex);
  }
}
