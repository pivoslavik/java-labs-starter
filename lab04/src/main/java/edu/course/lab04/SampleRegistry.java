package edu.course.lab04;

import java.util.HashMap;

public class SampleRegistry {
  private final HashMap<SampleId, DataSample> registry = new HashMap<>();

  public void put(SampleId id, DataSample sample) {
    registry.put(id, sample);
  }

  public DataSample get(SampleId id) {
    return registry.get(id);
  }

  public DataSample remove(SampleId id) {
    return registry.remove(id);
  }

  public boolean containsKey(SampleId id) {
    return registry.containsKey(id);
  }

  public int size() {
    return registry.size();
  }
}