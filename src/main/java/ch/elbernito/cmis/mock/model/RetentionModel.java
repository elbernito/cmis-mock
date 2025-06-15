package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * JPA entity representing Retention.
 */
@Entity
@Table(name = "retention")
public class RetentionModel {

    @Id
    private String id;

    @Column(name = "object_id", nullable = false)
    private String objectId;

    @Column(name = "retention_policy_name", nullable = false)
    private String retentionPolicyName;

    @Column(name = "retain_until", nullable = false)
    private Instant retainUntil;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    // Getters and setters...

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
}
