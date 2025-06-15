package ch.elbernito.cmis.mock.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.time.Instant;

/**
 * Data transfer object for Retention.
 */
public class RetentionDto {
    private String id;
    private String objectId;
    private String retentionPolicyName;
    private Instant retainUntil;
    private Instant createdAt;

    public RetentionDto() { }

    public RetentionDto(String id, String objectId, String retentionPolicyName, Instant retainUntil, Instant createdAt) {
        this.id = id;
        this.objectId = objectId;
        this.retentionPolicyName = retentionPolicyName;
        this.retainUntil = retainUntil;
        this.createdAt = createdAt;
    }

    // getters & setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getObjectId() { return objectId; }
    public void setObjectId(String objectId) { this.objectId = objectId; }
    public String getRetentionPolicyName() { return retentionPolicyName; }
    public void setRetentionPolicyName(String retentionPolicyName) { this.retentionPolicyName = retentionPolicyName; }
    public Instant getRetainUntil() { return retainUntil; }
    public void setRetainUntil(Instant retainUntil) { this.retainUntil = retainUntil; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
