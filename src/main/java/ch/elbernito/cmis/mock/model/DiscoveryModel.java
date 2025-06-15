package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * JPA entity representing a Discovery query.
 */
@Entity
@Table(name = "discovery")
public class DiscoveryModel {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "query", nullable = false, length = 1000)
    private String query;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public DiscoveryModel() { }

    public DiscoveryModel(String id, String query, String description, Instant createdAt) {
        this.id = id;
        this.query = query;
        this.description = description;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
