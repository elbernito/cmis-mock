package ch.elbernito.cmis.mock.cmis12.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity representing a CMIS 1.2 Change Event for the change log.
 */
@Entity
@Table(name = "change_logs")
public class ChangeLogModel {
    private static final Logger logger = LoggerFactory.getLogger(ChangeLogModel.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The objectId of the changed object.
     */
    @Column(name = "object_id", nullable = false)
    private String objectId;

    /**
     * Change type (created, updated, deleted).
     */
    @Column(name = "change_type", nullable = false)
    private String changeType;

    /**
     * Date/time of the change event.
     */
    @Column(name = "change_time", nullable = false)
    private LocalDateTime changeTime;

    /**
     * User who made the change.
     */
    @Column(name = "changed_by")
    private String changedBy;

    // --- Getter/Setter ---
    public Long getId() {
        logger.debug("Getting change log id");
        return id;
    }
    public void setId(Long id) {
        logger.debug("Setting change log id: {}", id);
        this.id = id;
    }
    public String getObjectId() {
        logger.debug("Getting objectId");
        return objectId;
    }
    public void setObjectId(String objectId) {
        logger.debug("Setting objectId: {}", objectId);
        this.objectId = objectId;
    }
    public String getChangeType() {
        logger.debug("Getting changeType");
        return changeType;
    }
    public void setChangeType(String changeType) {
        logger.debug("Setting changeType: {}", changeType);
        this.changeType = changeType;
    }
    public LocalDateTime getChangeTime() {
        logger.debug("Getting changeTime");
        return changeTime;
    }
    public void setChangeTime(LocalDateTime changeTime) {
        logger.debug("Setting changeTime: {}", changeTime);
        this.changeTime = changeTime;
    }
    public String getChangedBy() {
        logger.debug("Getting changedBy");
        return changedBy;
    }
    public void setChangedBy(String changedBy) {
        logger.debug("Setting changedBy: {}", changedBy);
        this.changedBy = changedBy;
    }

    @Override
    public String toString() {
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.toString(this);
    }
}
