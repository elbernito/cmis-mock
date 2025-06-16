package ch.elbernito.cmis.mock.cmis12.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity representing a CMIS 1.2 Property (for Document, Folder, ...).
 */
@Entity
@Table(name = "properties")
public class PropertyModel {
    private static final Logger logger = LoggerFactory.getLogger(PropertyModel.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The objectId to which this property belongs.
     */
    @Column(name = "object_id", nullable = false)
    private String objectId;

    /**
     * Property id/key (e.g., "cmis:name").
     */
    @Column(name = "property_id", nullable = false)
    private String propertyId;

    /**
     * Value as string (always filled, but can be parsed by type).
     */
    @Column(name = "property_value")
    private String propertyValue;

    /**
     * Data type (STRING, INTEGER, BOOLEAN, DATETIME, DECIMAL, HTML, ID, URI, ...).
     */
    @Column(name = "property_type", nullable = false)
    private String propertyType;

    /**
     * Multi-value (if true, value is comma-separated list).
     */
    @Column(name = "multi_valued")
    private Boolean multiValued = false;

    /**
     * Last modified date for the property.
     */
    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    // --- Getter/Setter ---
    public Long getId() {
        logger.debug("Getting property id");
        return id;
    }
    public void setId(Long id) {
        logger.debug("Setting property id: {}", id);
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
    public String getPropertyId() {
        logger.debug("Getting propertyId");
        return propertyId;
    }
    public void setPropertyId(String propertyId) {
        logger.debug("Setting propertyId: {}", propertyId);
        this.propertyId = propertyId;
    }
    public String getPropertyValue() {
        logger.debug("Getting propertyValue");
        return propertyValue;
    }
    public void setPropertyValue(String propertyValue) {
        logger.debug("Setting propertyValue: {}", propertyValue);
        this.propertyValue = propertyValue;
    }
    public String getPropertyType() {
        logger.debug("Getting propertyType");
        return propertyType;
    }
    public void setPropertyType(String propertyType) {
        logger.debug("Setting propertyType: {}", propertyType);
        this.propertyType = propertyType;
    }
    public Boolean getMultiValued() {
        logger.debug("Getting multiValued");
        return multiValued;
    }
    public void setMultiValued(Boolean multiValued) {
        logger.debug("Setting multiValued: {}", multiValued);
        this.multiValued = multiValued;
    }
    public LocalDateTime getLastModified() {
        logger.debug("Getting lastModified");
        return lastModified;
    }
    public void setLastModified(LocalDateTime lastModified) {
        logger.debug("Setting lastModified: {}", lastModified);
        this.lastModified = lastModified;
    }
    @Override
    public String toString() {
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.toString(this);
    }
}
