package ch.elbernito.cmis.mock.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.time.Instant;

/**
 * Data transfer object for Type Definition.
 */
public class TypeDefinitionDto {
    private String id;
    private String baseType;
    private String typeId;
    private String displayName;
    private String description;
    private Instant createdAt;

    public TypeDefinitionDto() { }

    public TypeDefinitionDto(String id, String baseType, String typeId, String displayName, String description, Instant createdAt) {
        this.id = id;
        this.baseType = baseType;
        this.typeId = typeId;
        this.displayName = displayName;
        this.description = description;
        this.createdAt = createdAt;
    }

    // getters & setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBaseType() { return baseType; }
    public void setBaseType(String baseType) { this.baseType = baseType; }
    public String getTypeId() { return typeId; }
    public void setTypeId(String typeId) { this.typeId = typeId; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
