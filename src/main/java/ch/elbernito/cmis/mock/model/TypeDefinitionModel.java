package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * JPA entity representing a Type Definition.
 */
@Entity
@Table(name = "type_definitions")
public class TypeDefinitionModel {

    @Id
    private String id;

    @Column(name = "base_type", nullable = false)
    private String baseType;

    @Column(name = "type_id", nullable = false)
    private String typeId;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    // Getters and setters...

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
}
