package edu.course.lab07;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataAnalytics {
  public List<Observation> filterByStatus(List<Observation> items, String targetStatus) {
    return items.stream()
        .filter(item -> item.status().equalsIgnoreCase(targetStatus))
        .toList();
  }

  public List<String> getNormalizedLabels(List<Observation> items) {
    return items.stream()
        .map(item -> item.label().strip().toLowerCase())
        .distinct()
        .sorted()
        .toList();
  }

  public Map<String, Long> countByLabel(List<Observation> items) {
    return items.stream()
        .collect(Collectors.groupingBy(
            Observation::label,
            Collectors.counting()
        ));
  }

  public Optional<Observation> findMaxByFeatureCount(List<Observation> items) {
    return items.stream()
        .max(Comparator.comparingInt(Observation::featureCount));
  }

  public List<Observation> sortByFeaturesAndSource(List<Observation> items) {
    return items.stream()
        .sorted(Comparator.comparingInt(Observation::featureCount)
            .thenComparing(Observation::source))
        .toList();
  }

  public Map<String, Double> averageFeatureCountByLabel(List<Observation> items) {
    return items.stream()
        .collect(Collectors.groupingBy(
            Observation::label,
            Collectors.averagingDouble(Observation::featureCount)
        ));
  }
}
