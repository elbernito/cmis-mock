package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * JPA entity representing a Version.
 */
@Entity
@Table(name = "versions")
public class VersionModel {

    @Id
    private String id;

    @Column(name = "object_id", nullable = false)
    private String objectId;

    @Column(name = "version_label", nullable = false)
    private String versionLabel;

    @Column(name = "latest", nullable = false)
    private boolean latest;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    // Getters and setters...

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getObjectId() { return objectId; }
    public void setObjectId(String objectId) { this.objectId = objectId; }
    public String getVersionLabel() { return versionLabel; }
    public void setVersionLabel(String versionLabel) { this.versionLabel = versionLabel; }
    public boolean isLatest() { return latest; }
    public void setLatest(boolean latest) { this.latest = latest; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
