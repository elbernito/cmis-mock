package ch.elbernito.cmismock.cmis12.model;

import ch.elbernito.cmis.mock.cmis12.model.CmisObjectModel;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity representing a CMIS 1.2 Folder object.
 */
@Entity
@Table(name = "folders")
public class FolderModel extends CmisObjectModel {
    private static final Logger logger = LoggerFactory.getLogger(FolderModel.class);

    /**
     * The parent folder id (null if this is the root folder).
     */
    @Column(name = "parent_id")
    private String parentId;

    // --- Getter/Setter ---
    public String getParentId() {
        logger.debug("Getting parentId");
        return parentId;
    }
    public void setParentId(String parentId) {
        logger.debug("Setting parentId: {}", parentId);
        this.parentId = parentId;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
