package ch.elbernito.cmis.mock.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.time.Instant;

/**
 * Data transfer object for Relationship.
 */
public class RelationshipDto {
    private String id;
    private String sourceId;
    private String targetId;
    private String relationType;
    private Instant createdAt;

    public RelationshipDto() { }

    public RelationshipDto(String id, String sourceId, String targetId, String relationType, Instant createdAt) {
        this.id = id;
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.relationType = relationType;
        this.createdAt = createdAt;
    }

    // getters & setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSourceId() { return sourceId; }
    public void setSourceId(String sourceId) { this.sourceId = sourceId; }
    public String getTargetId() { return targetId; }
    public void setTargetId(String targetId) { this.targetId = targetId; }
    public String getRelationType() { return relationType; }
    public void setRelationType(String relationType) { this.relationType = relationType; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
