package ch.elbernito.cmis.mock.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.time.Instant;

/**
 * Data transfer object for Metadata.
 */
public class MetadataDto {
    private String id;
    private String objectId;
    private String propertyName;
    private String propertyValue;
    private Instant createdAt;

    public MetadataDto() { }

    public MetadataDto(String id, String objectId, String propertyName, String propertyValue, Instant createdAt) {
        this.id = id;
        this.objectId = objectId;
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.createdAt = createdAt;
    }

    // getters & setters

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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
