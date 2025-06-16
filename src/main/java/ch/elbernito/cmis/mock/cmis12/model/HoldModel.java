package ch.elbernito.cmis.mock.cmis12.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity representing a CMIS 1.2 Hold object (optional, for legal holds/locks).
 */
@Entity
@Table(name = "holds")
public class HoldModel extends CmisObjectModel {
    private static final Logger logger = LoggerFactory.getLogger(HoldModel.class);

    /**
     * The target object id (document/folder under hold).
     */
    @Column(name = "target_object_id", nullable = false)
    private String targetObjectId;

    /**
     * Hold reason/description.
     */
    @Column(name = "reason")
    private String reason;

    /**
     * Hold start date.
     */
    @Column(name = "start_date")
    private LocalDateTime startDate;

    /**
     * Hold release date (optional).
     */
    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    // --- Getter/Setter ---
    public String getTargetObjectId() {
        logger.debug("Getting targetObjectId");
        return targetObjectId;
    }
    public void setTargetObjectId(String targetObjectId) {
        logger.debug("Setting targetObjectId: {}", targetObjectId);
        this.targetObjectId = targetObjectId;
    }
    public String getReason() {
        logger.debug("Getting reason");
        return reason;
    }
    public void setReason(String reason) {
        logger.debug("Setting reason: {}", reason);
        this.reason = reason;
    }
    public LocalDateTime getStartDate() {
        logger.debug("Getting startDate");
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        logger.debug("Setting startDate: {}", startDate);
        this.startDate = startDate;
    }
    public LocalDateTime getReleaseDate() {
        logger.debug("Getting releaseDate");
        return releaseDate;
    }
    public void setReleaseDate(LocalDateTime releaseDate) {
        logger.debug("Setting releaseDate: {}", releaseDate);
        this.releaseDate = releaseDate;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
