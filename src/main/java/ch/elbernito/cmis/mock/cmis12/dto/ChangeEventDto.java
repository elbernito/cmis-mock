package ch.elbernito.cmis.mock.cmis12.dto;

import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * DTO for a single CMIS ChangeEvent.
 */
public class ChangeEventDto {
    private String objectId;
    private String changeType;
    private LocalDateTime changeTime;
    private String changedBy;

    public String getObjectId() { return objectId; }
    public void setObjectId(String objectId) { this.objectId = objectId; }
    public String getChangeType() { return changeType; }
    public void setChangeType(String changeType) { this.changeType = changeType; }
    public LocalDateTime getChangeTime() { return changeTime; }
    public void setChangeTime(LocalDateTime changeTime) { this.changeTime = changeTime; }
    public String getChangedBy() { return changedBy; }
    public void setChangedBy(String changedBy) { this.changedBy = changedBy; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
