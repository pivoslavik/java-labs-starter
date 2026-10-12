package edu.course.lab06;

import java.util.Collection;
import java.util.Objects;

public class CollectionUtils {

  public static <T> void copyAll(Collection<? extends T> src, Collection<? super T> dest) {
    Objects.requireNonNull(src, "source collection must not be null");
    Objects.requireNonNull(dest, "destination collection must not be null");

    dest.addAll(src);
  }
}
