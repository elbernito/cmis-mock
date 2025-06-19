package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a relationship between two CMIS objects.
 */
@Entity
@Table(name = "relationship")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class RelationshipModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relationship UUID (external CMIS id).
     */
    @Column(name = "relationship_id", nullable = false, unique = true, updatable = false, length = 36)
    private String relationshipId;

    /**
     * Source object ID (UUID).
     */
    @Column(name = "source_id", nullable = false, length = 36)
    private String sourceId;

    /**
     * Target object ID (UUID).
     */
    @Column(name = "target_id", nullable = false, length = 36)
    private String targetId;

    /**
     * Relationship type.
     */
    @Column(name = "relationship_type", length = 255)
    private String relationshipType;

    /**
     * Creation time.
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    /**
     * Set UUID and creation date automatically.
     */
    @PrePersist
    public void prePersist() {
        if (relationshipId == null || relationshipId.isEmpty()) {
            relationshipId = UUID.randomUUID().toString();
            log.debug("Generated new Relationship UUID: {}", relationshipId);
        }
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
    }
}
