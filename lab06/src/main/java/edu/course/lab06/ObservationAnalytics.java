package edu.course.lab06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObservationAnalytics {
  public Map<String, Integer> countByStatus(List<Observation> observations) {
    Map<String, Integer> counts = new HashMap<>();
    for (Observation obs : observations) {
      String status = obs.getStatus();
      counts.put(status, counts.getOrDefault(status, 0) + 1);
    }
    return counts;
  }

  public Set<String> getUniqueSources(List<Observation> observations) {
    Set<String> sources = new HashSet<>();
    for (Observation obs : observations) {
      sources.add(obs.getSource());
    }
    return sources;
  }

  public List<Observation> sortByFeaturesAndId(List<Observation> observations) {
    List<Observation> sorted = new ArrayList<>(observations);
    Comparator<Observation> comparator = Comparator
        .comparingInt(Observation::getFeatureCount)
        .thenComparingLong(Observation::getId);
    sorted.sort(comparator);
    return sorted;
  }
}