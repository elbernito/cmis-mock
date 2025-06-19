package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.UUID;

/**
 * Entity representing a CMIS TypeDefinition.
 */
@Entity
@Table(name = "typedefinition")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class TypeDefinitionModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * TypeDefinition UUID (external CMIS id).
     */
    @Column(name = "type_id", nullable = false, unique = true, updatable = false, length = 36)
    private String typeId;

    /**
     * Human-readable name.
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Description.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Parent type id (nullable for root types).
     */
    @Column(name = "parent_type_id", length = 36)
    private String parentTypeId;

    /**
     * Is this type creatable?
     */
    @Column(name = "creatable")
    private Boolean creatable;

    /**
     * Set UUID automatically.
     */
    @PrePersist
    public void prePersist() {
        if (typeId == null || typeId.isEmpty()) {
            typeId = UUID.randomUUID().toString();
            log.debug("Generated new TypeDefinition UUID: {}", typeId);
        }
    }
}
