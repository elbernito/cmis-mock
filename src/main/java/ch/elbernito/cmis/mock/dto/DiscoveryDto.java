package ch.elbernito.cmis.mock.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.time.Instant;

/**
 * Data transfer object for Discovery.
 */
public class DiscoveryDto {
    private String id;
    private String query;
    private String description;
    private Instant createdAt;

    public DiscoveryDto() { }

    public DiscoveryDto(String id, String query, String description, Instant createdAt) {
        this.id = id;
        this.query = query;
        this.description = description;
        this.createdAt = createdAt;
    }

    // getters & setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
