package ch.elbernito.cmis.mock.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.time.Instant;

/**
 * Data transfer object for Statistics.
 */
public class StatisticsDto {
    private String id;
    private String metricName;
    private double metricValue;
    private Instant createdAt;

    public StatisticsDto() { }

    public StatisticsDto(String id, String metricName, double metricValue, Instant createdAt) {
        this.id = id;
        this.metricName = metricName;
        this.metricValue = metricValue;
        this.createdAt = createdAt;
    }

    // getters & setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMetricName() { return metricName; }
    public void setMetricName(String metricName) { this.metricName = metricName; }
    public double getMetricValue() { return metricValue; }
    public void setMetricValue(double metricValue) { this.metricValue = metricValue; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
