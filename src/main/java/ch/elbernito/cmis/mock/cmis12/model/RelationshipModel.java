package ch.elbernito.cmismock.cmis12.model;

import ch.elbernito.cmis.mock.cmis12.model.CmisObjectModel;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity representing a CMIS 1.2 Relationship object.
 */
@Entity
@Table(name = "relationships")
public class RelationshipModel extends CmisObjectModel {
    private static final Logger logger = LoggerFactory.getLogger(RelationshipModel.class);

    /**
     * Source object id.
     */
    @Column(name = "source_id", nullable = false)
    private String sourceId;

    /**
     * Target object id.
     */
    @Column(name = "target_id", nullable = false)
    private String targetId;

    // --- Getter/Setter ---
    public String getSourceId() {
        logger.debug("Getting sourceId");
        return sourceId;
    }
    public void setSourceId(String sourceId) {
        logger.debug("Setting sourceId: {}", sourceId);
        this.sourceId = sourceId;
    }
    public String getTargetId() {
        logger.debug("Getting targetId");
        return targetId;
    }
    public void setTargetId(String targetId) {
        logger.debug("Setting targetId: {}", targetId);
        this.targetId = targetId;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
