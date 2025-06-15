package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * JPA entity representing Metadata.
 */
@Entity
@Table(name = "metadata")
public class MetadataModel {

    @Id
    private String id;

    @Column(name = "object_id", nullable = false)
    private String objectId;

    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @Column(name = "property_value", nullable = false)
    private String propertyValue;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    // Getters and setters...

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getObjectId() { return objectId; }
    public void setObjectId(String objectId) { this.objectId = objectId; }
    public String getPropertyName() { return propertyName; }
    public void setPropertyName(String propertyName) { this.propertyName = propertyName; }
    public String getPropertyValue() { return propertyValue; }
    public void setPropertyValue(String propertyValue) { this.propertyValue = propertyValue; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
