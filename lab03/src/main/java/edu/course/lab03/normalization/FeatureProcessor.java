package edu.course.lab03.normalization;


public class FeatureProcessor {
  private NormalizationStrategy normalization;

  public FeatureProcessor(NormalizationStrategy normalization) {
    if (normalization == null) {
      throw new IllegalArgumentException("Normalization is null");
    }
    this.normalization = normalization;
  }

  public void setNormalization(NormalizationStrategy normalization) {
    if (normalization == null) {
      throw new IllegalArgumentException("Normalization is null");
    }
    this.normalization = normalization;
  }

  public double[] calculateNormalization(double[] values) {
    return normalization.normalize(values);
  }
}
