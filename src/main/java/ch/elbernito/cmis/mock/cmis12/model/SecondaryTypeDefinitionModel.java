package ch.elbernito.cmis.mock.cmis12.model;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity representing a CMIS 1.2 Secondary Type Definition.
 */
@Entity
@Table(name = "secondary_type_definitions")
public class SecondaryTypeDefinitionModel extends CmisObjectModel {
    private static final Logger logger = LoggerFactory.getLogger(SecondaryTypeDefinitionModel.class);

    /**
     * Parent type id (optional).
     */
    @Column(name = "parent_type_id")
    private String parentTypeId;

    /**
     * Description of the secondary type (optional).
     */
    @Column(name = "description")
    private String description;

    // --- Getter/Setter ---
    public String getParentTypeId() {
        logger.debug("Getting parentTypeId");
        return parentTypeId;
    }
    public void setParentTypeId(String parentTypeId) {
        logger.debug("Setting parentTypeId: {}", parentTypeId);
        this.parentTypeId = parentTypeId;
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
