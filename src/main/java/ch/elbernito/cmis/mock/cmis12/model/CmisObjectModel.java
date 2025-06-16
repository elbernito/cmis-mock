package ch.elbernito.cmis.mock.cmis12.model;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Abstract base class for all persistent CMIS 1.2 objects.
 * Contains core CMIS properties.
 */
@MappedSuperclass
public abstract class CmisObjectModel implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(CmisObjectModel.class);

    /**
     * The unique object id (CMIS property: cmis:objectId).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "object_id", nullable = false, updatable = false, unique = true)
    protected String id;

    /**
     * The name of the object (CMIS property: cmis:name).
     */
    @Column(name = "name", nullable = false)
    protected String name;

    /**
     * The type id (CMIS property: cmis:objectTypeId).
     */
    @Column(name = "object_type_id", nullable = false)
    protected String objectTypeId;

    /**
     * The creation date (CMIS property: cmis:creationDate).
     */
    @Column(name = "creation_date", nullable = false)
    protected LocalDateTime creationDate;

    /**
     * The last modification date (CMIS property: cmis:lastModificationDate).
     */
    @Column(name = "last_modification_date")
    protected LocalDateTime lastModificationDate;

    /**
     * The user who created the object (CMIS property: cmis:createdBy).
     */
    @Column(name = "created_by", nullable = false)
    protected String createdBy;

    /**
     * The user who last modified the object (CMIS property: cmis:lastModifiedBy).
     */
    @Column(name = "last_modified_by")
    protected String lastModifiedBy;

    // -- Optional: Multi-valued properties, extensions --
    // Implement property extension in own tables

    // --- Getter and Setter ---

    public String getId() {
        logger.debug("Getting object id");
        return id;
    }
    public void setId(String id) {
        logger.debug("Setting object id: {}", id);
        this.id = id;
    }
    public String getName() {
        logger.debug("Getting object name");
        return name;
    }
    public void setName(String name) {
        logger.debug("Setting object name: {}", name);
        this.name = name;
    }
    public String getObjectTypeId() {
        logger.debug("Getting objectTypeId");
        return objectTypeId;
    }
    public void setObjectTypeId(String objectTypeId) {
        logger.debug("Setting objectTypeId: {}", objectTypeId);
        this.objectTypeId = objectTypeId;
    }
    public LocalDateTime getCreationDate() {
        logger.debug("Getting creationDate");
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        logger.debug("Setting creationDate: {}", creationDate);
        this.creationDate = creationDate;
    }
    public LocalDateTime getLastModificationDate() {
        logger.debug("Getting lastModificationDate");
        return lastModificationDate;
    }
    public void setLastModificationDate(LocalDateTime lastModificationDate) {
        logger.debug("Setting lastModificationDate: {}", lastModificationDate);
        this.lastModificationDate = lastModificationDate;
    }
    public String getCreatedBy() {
        logger.debug("Getting createdBy");
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        logger.debug("Setting createdBy: {}", createdBy);
        this.createdBy = createdBy;
    }
    public String getLastModifiedBy() {
        logger.debug("Getting lastModifiedBy");
        return lastModifiedBy;
    }
    public void setLastModifiedBy(String lastModifiedBy) {
        logger.debug("Setting lastModifiedBy: {}", lastModifiedBy);
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
