package edu.course.lab03;

import edu.course.lab03.normalization.FeatureProcessor;
import edu.course.lab03.normalization.MeanCenteringNormalization;
import edu.course.lab03.normalization.MinMaxNormalization;
import edu.course.lab03.normalization.NormalizationStrategy;
import edu.course.lab03.normalization.ZScoreNormalization;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class NormalizationTest {

  @Test
  public void minMaxNormalizationTransformsValuesToUnitInterval() {
    NormalizationStrategy strategy = new MinMaxNormalization();
    double[] input = {1.0, 2.0, 3.0};
    double[] expected = {0.0, 0.5, 1.0};
    assertArrayEquals(expected, strategy.normalize(input), 1e-4);
  }

  @Test
  public void minMaxNormalizationWithEqualValuesReturnsZeros() {
    NormalizationStrategy strategy = new MinMaxNormalization();
    double[] input = {5.0, 5.0, 5.0};
    double[] expected = {0.0, 0.0, 0.0};
    assertArrayEquals(expected, strategy.normalize(input), 1e-4);
  }

  @Test
  public void meanCenteringNormalizationSubtractsMeanFromValues() {
    NormalizationStrategy strategy = new MeanCenteringNormalization();
    double[] input = {10.0, 20.0, 30.0};
    double[] expected = {-10.0, 0.0, 10.0};
    assertArrayEquals(expected, strategy.normalize(input), 1e-4);
  }

  @Test
  public void meanCenteringNormalizationWithSingleElementReturnsZero() {
    NormalizationStrategy strategy = new MeanCenteringNormalization();
    double[] input = {5.0};
    double[] expected = {0.0};
    assertArrayEquals(expected, strategy.normalize(input), 1e-4);
  }

  @Test
  public void zScoreNormalizationScalesValuesToZeroMeanAndUnitVariance() {
    NormalizationStrategy strategy = new ZScoreNormalization();
    double[] input = {1.0, 5.0};
    double[] expected = {-1.0, 1.0};
    assertArrayEquals(expected, strategy.normalize(input), 1e-4);
  }

  @Test
  public void zScoreNormalizationWithConstantValuesReturnsZeros() {
    NormalizationStrategy strategy = new ZScoreNormalization();
    double[] input = {3.0, 3.0, 3.0};
    double[] expected = {0.0, 0.0, 0.0};
    assertArrayEquals(expected, strategy.normalize(input), 1e-4);
  }

  @Test
  public void changeNormalizationStrategyInFeatureProcessorReturnsAnotherValue() {
    double[] input = {1.0, 2.0, 3.0};
    FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
    assertArrayEquals(new double[]{0.0, 0.5, 1.0}, processor.calculateNormalization(input), 1e-4);
    processor.setNormalization(new MeanCenteringNormalization());
    assertArrayEquals(new double[]{-1.0, 0.0, 1.0}, processor.calculateNormalization(input), 1e-4);
    processor.setNormalization(new ZScoreNormalization());
    assertArrayEquals(new double[]{-1.2247, 0.0, 1.2247}, processor.calculateNormalization(input), 1e-4);
  }

  @Test
  public void featureProcessorDoesNotModifyInputArray() {
    double[] input = {10.0, 20.0, 30.0};
    double[] originalCopy = {10.0, 20.0, 30.0};
    FeatureProcessor processor = new FeatureProcessor(new MeanCenteringNormalization());
    processor.calculateNormalization(input);
    assertArrayEquals(originalCopy, input, 1e-4);
  }
}