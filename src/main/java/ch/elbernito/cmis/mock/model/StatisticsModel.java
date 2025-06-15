package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * JPA entity representing Statistics.
 */
@Entity
@Table(name = "statistics")
public class StatisticsModel {

    @Id
    private String id;

    @Column(name = "metric_name", nullable = false)
    private String metricName;

    @Column(name = "metric_value", nullable = false)
    private double metricValue;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    // Getters and setters...

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMetricName() { return metricName; }
    public void setMetricName(String metricName) { this.metricName = metricName; }
    public double getMetricValue() { return metricValue; }
    public void setMetricValue(double metricValue) { this.metricValue = metricValue; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
