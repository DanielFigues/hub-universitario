package br.edu.hub.dto;

public record DashBoardMetrics(
        long totalActivities,
        long totalRegistrations,
        long openActivities,
        long fullActivities
) {
}