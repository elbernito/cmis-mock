package ch.elbernito.cmis.mock.cmis12.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * DTO for a single CMIS PropertyDefinition.
 */
public class PropertyDefinitionDto {
    private String id;
    private String displayName;
    private String propertyType;
    private boolean required;
    private boolean updatable;
    private boolean multiValued;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getPropertyType() { return propertyType; }
    public void setPropertyType(String propertyType) { this.propertyType = propertyType; }
    public boolean isRequired() { return required; }
    public void setRequired(boolean required) { this.required = required; }
    public boolean isUpdatable() { return updatable; }
    public void setUpdatable(boolean updatable) { this.updatable = updatable; }
    public boolean isMultiValued() { return multiValued; }
    public void setMultiValued(boolean multiValued) { this.multiValued = multiValued; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
