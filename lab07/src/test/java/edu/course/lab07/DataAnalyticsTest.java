package edu.course.lab07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataAnalyticsTest {

  private DataAnalytics analytics;

  @BeforeEach
  void setUp() {
    analytics = new DataAnalytics();
  }

  @Test
  void shouldReturnEmptyListWhenFilteringEmptyCollection() {
    List<Observation> result = analytics.filterByStatus(List.of(), "READY");
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldFilterByStatusCorrectly() {
    List<Observation> items = List.of(
        new Observation("READY", "Camera", "car", 12),
        new Observation("PENDING", "Lidar", "pedestrian", 4),
        new Observation("READY", "Radar", "car", 8)
    );

    List<Observation> result = analytics.filterByStatus(items, "READY");

    assertEquals(2, result.size());
    assertTrue(result.stream().allMatch(item -> item.status().equals("READY")));
  }

  @Test
  void shouldNormalizeAndSortLabelsWithoutDuplicates() {
    List<Observation> items = List.of(
        new Observation("READY", "Camera", " Car ", 10),
        new Observation("READY", "Lidar", "pedestrian", 5),
        new Observation("READY", "Radar", "car", 8),
        new Observation("READY", "Sonar", "BIKE", 2)
    );

    List<String> result = analytics.getNormalizedLabels(items);

    assertEquals(List.of("bike", "car", "pedestrian"), result);
  }

  @Test
  void shouldCountObservationsByGroup() {
    List<Observation> items = List.of(
        new Observation("READY", "Camera", "car", 10),
        new Observation("READY", "Lidar", "car", 5),
        new Observation("READY", "Radar", "tree", 2)
    );

    Map<String, Long> counts = analytics.countByLabel(items);

    assertEquals(2L, counts.get("car"));
    assertEquals(1L, counts.get("tree"));
  }

  @Test
  void shouldReturnEmptyOptionalWhenFindingMaxOnEmptyList() {
    Optional<Observation> max = analytics.findMaxByFeatureCount(List.of());
    assertTrue(max.isEmpty());
  }

  @Test
  void shouldReturnMaxElementForSingleAndMultipleItems() {
    Observation single = new Observation("READY", "Cam", "sign", 7);
    Optional<Observation> singleResult = analytics.findMaxByFeatureCount(List.of(single));
    assertEquals(single, singleResult.orElseThrow());

    List<Observation> items = List.of(
        new Observation("READY", "Cam1", "sign", 3),
        new Observation("READY", "Cam2", "sign", 15),
        new Observation("READY", "Cam3", "sign", 8)
    );
    Optional<Observation> multiResult = analytics.findMaxByFeatureCount(items);
    assertEquals(15, multiResult.orElseThrow().featureCount());
  }

  @Test
  void shouldSortByFeaturesThenBySourceWhenPrimaryKeysAreEqual() {
    Observation item1 = new Observation("READY", "Z-Sensor", "obj", 10);
    Observation item2 = new Observation("READY", "A-Sensor", "obj", 10);
    Observation item3 = new Observation("READY", "M-Sensor", "obj", 5);

    List<Observation> sorted = analytics.sortByFeaturesAndSource(List.of(item1, item2, item3));

    assertEquals(List.of(item3, item2, item1), sorted);
  }

  @Test
  void shouldNotMutateInputListDuringOperations() {
    List<Observation> original = new ArrayList<>();
    original.add(new Observation("READY", "Camera", "car", 12));
    original.add(new Observation("FAILED", "Radar", "truck", 6));

    List<Observation> copy = List.copyOf(original);

    analytics.filterByStatus(original, "READY");
    analytics.getNormalizedLabels(original);
    analytics.countByLabel(original);
    analytics.findMaxByFeatureCount(original);
    analytics.sortByFeaturesAndSource(original);

    assertEquals(copy, original);
  }

  @Test
  void shouldReturnEmptyMapWhenCalculatingAverageOnEmptyList() {
    Map<String, Double> result = analytics.averageFeatureCountByLabel(List.of());
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldCalculateAverageForSingleItem() {
    List<Observation> items = List.of(
        new Observation("READY", "Camera", "car", 15)
    );

    Map<String, Double> result = analytics.averageFeatureCountByLabel(items);

    assertEquals(1, result.size());
    assertEquals(15.0, result.get("car"), 0.001);
  }

  @Test
  void shouldCalculateAverageFeatureCountGroupedByLabel() {
    List<Observation> items = List.of(
        new Observation("READY", "Camera", "car", 10),
        new Observation("READY", "Lidar", "car", 20),
        new Observation("READY", "Radar", "car", 15),
        new Observation("READY", "Camera", "pedestrian", 4),
        new Observation("READY", "Lidar", "pedestrian", 6)
    );

    Map<String, Double> result = analytics.averageFeatureCountByLabel(items);

    assertEquals(2, result.size());
    assertEquals(15.0, result.get("car"), 0.001);
    assertEquals(5.0, result.get("pedestrian"), 0.001);
  }

  @Test
  void shouldNotMutateInputListWhenCalculatingAverage() {
    List<Observation> original = new ArrayList<>();
    original.add(new Observation("READY", "Camera", "car", 10));
    original.add(new Observation("READY", "Radar", "car", 20));

    List<Observation> copy = List.copyOf(original);

    analytics.averageFeatureCountByLabel(original);

    assertEquals(copy, original);
  }
}