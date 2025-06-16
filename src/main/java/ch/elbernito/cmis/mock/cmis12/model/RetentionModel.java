package ch.elbernito.cmis.mock.cmis12.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity representing a CMIS 1.2 Retention object (optional).
 */
@Entity
@Table(name = "retentions")
public class RetentionModel extends CmisObjectModel {
    private static final Logger logger = LoggerFactory.getLogger(RetentionModel.class);

    /**
     * The target object id (e.g. document/folder under retention).
     */
    @Column(name = "target_object_id", nullable = false)
    private String targetObjectId;

    /**
     * Retention start date.
     */
    @Column(name = "start_date")
    private LocalDateTime startDate;

    /**
     * Retention end date (optional).
     */
    @Column(name = "end_date")
    private LocalDateTime endDate;

    /**
     * Retention description (optional).
     */
    @Column(name = "description")
    private String description;

    // --- Getter/Setter ---
    public String getTargetObjectId() {
        logger.debug("Getting targetObjectId");
        return targetObjectId;
    }
    public void setTargetObjectId(String targetObjectId) {
        logger.debug("Setting targetObjectId: {}", targetObjectId);
        this.targetObjectId = targetObjectId;
    }
    public LocalDateTime getStartDate() {
        logger.debug("Getting startDate");
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        logger.debug("Setting startDate: {}", startDate);
        this.startDate = startDate;
    }
    public LocalDateTime getEndDate() {
        logger.debug("Getting endDate");
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        logger.debug("Setting endDate: {}", endDate);
        this.endDate = endDate;
    }
    public String getDescription() {
        logger.debug("Getting description");
        return description;
    }
    public void setDescription(String description) {
        logger.debug("Setting description: {}", description);
        this.description = description;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
