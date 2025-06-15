package ch.elbernito.cmis.mock.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.time.Instant;

/**
 * Data transfer object for Version.
 */
public class VersionDto {
    private String id;
    private String objectId;
    private String versionLabel;
    private boolean latest;
    private Instant createdAt;

    public VersionDto() { }

    public VersionDto(String id, String objectId, String versionLabel, boolean latest, Instant createdAt) {
        this.id = id;
        this.objectId = objectId;
        this.versionLabel = versionLabel;
        this.latest = latest;
        this.createdAt = createdAt;
    }

    // getters & setters

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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
