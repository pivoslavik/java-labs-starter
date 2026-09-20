package edu.course.lab02;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DataSampleTest {
  private DataSample sample;

  @BeforeEach
  void setup() {
    SampleId id = new SampleId("DS-1");
    double[] features = {1.0, 3.0, 2.0};
    sample = new DataSample(id.value(), "LABEL", SampleStatus.NOT_COMPLETED, features);
  }

  @ParameterizedTest
  @MethodSource("provideInvalidConstructorArgs")
  void onCreateWithInvalidConstructorArgsThrowsIllegalArgumentException(String id, String label, SampleStatus status, double[] features) {
    assertThrows(IllegalArgumentException.class, () -> new DataSample(id, label, status, features));
  }

  private static List<Arguments> provideInvalidConstructorArgs() {
    String validId = "DS-1";
    String validLabel = "LABEL";
    SampleStatus validStatus = SampleStatus.NOT_COMPLETED;
    double[] validFeatures = new double[]{1.0, 3.0, 2.0};

    return List.of(
        Arguments.of(null, validLabel, validStatus, validFeatures),
        Arguments.of("", validLabel, validStatus, validFeatures),
        Arguments.of("   ", validLabel, validStatus, validFeatures),
        Arguments.of(validId, null, validStatus, validFeatures),
        Arguments.of(validId, "", validStatus, validFeatures),
        Arguments.of(validId, "   ", validStatus, validFeatures),
        Arguments.of(validId, validLabel, null, validFeatures),
        Arguments.of(validId, validLabel, validStatus, null),
        Arguments.of(validId, validLabel, validStatus, new double[0])
    );
  }

  @Test
  void changeStatusReturnTrueIfChangeToCompleted() {
    assertFalse(sample.isCompleted());
    sample.changeStatus(SampleStatus.COMPLETED);
    assertTrue(sample.isCompleted());
  }

  @Test
  void changeStatusWithNullArgumentThrowsIllegalArgumentException() {
    assertFalse(sample.isCompleted());
    assertThrows(IllegalArgumentException.class, () -> sample.changeStatus(null));
  }

  @Test
  void averageReturnAverageOfArray() {
    assertEquals(2.0, sample.average(), 1e-4);
  }

  @Test
  void getFeaturesReturnImmutableArrayAfterUncontrolledStateMutationPrevention() {
    double[] original = {1.0, 2.0, 3.0};
    DataSample sample = new DataSample("DS-1", "Label", SampleStatus.NOT_COMPLETED, original);
    original[0] = 99.0;
    assertEquals(1.0, sample.getFeatures()[0]);
    double[] retrieved = sample.getFeatures();
    retrieved[0] = 88.0;
    assertEquals(1.0, sample.getFeatures()[0]);
  }

  @Test
  void normalizeFeaturesReturnCorrectNormalize() {
    double[] normalized = sample.normalizeFeatures();
    assertArrayEquals(new double[]{0.0, 1.0, 0.5}, normalized, 1e-6);
  }
}
