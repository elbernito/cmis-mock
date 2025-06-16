package ch.elbernito.cmis.mock.cmis12.model;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity representing a CMIS 1.1/1.2 Item object (optional object type).
 */
@Entity
@Table(name = "items")
public class ItemModel extends CmisObjectModel {
    private static final Logger logger = LoggerFactory.getLogger(ItemModel.class);

    /**
     * The parent folder id (optional).
     */
    @Column(name = "parent_id")
    private String parentId;

    // Item-specific custom fields (optional, can be extended)
    @Column(name = "custom_field")
    private String customField;

    // --- Getter/Setter ---
    public String getParentId() {
        logger.debug("Getting parentId");
        return parentId;
    }
    public void setParentId(String parentId) {
        logger.debug("Setting parentId: {}", parentId);
        this.parentId = parentId;
    }
    public String getCustomField() {
        logger.debug("Getting customField");
        return customField;
    }
    public void setCustomField(String customField) {
        logger.debug("Setting customField: {}", customField);
        this.customField = customField;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
