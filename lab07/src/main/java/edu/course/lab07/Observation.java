package edu.course.lab07;


public record Observation(
    String status,
    String source,
    String label,
    int featureCount
) {
}
