package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a single metadata entry for a document.
 */
@Entity
@Table(name = "metadata")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class MetadataModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Metadata UUID (external CMIS id).
     */
    @Column(name = "metadata_id", nullable = false, unique = true, updatable = false, length = 36)
    private String metadataId;

    /**
     * Document ID (UUID) to which this metadata belongs.
     */
    @Column(name = "document_id", nullable = false, length = 36)
    private String documentId;

    /**
     * Metadata key.
     */
    @Column(name = "meta_key", nullable = false, length = 255)
    private String key;

    /**
     * Metadata value.
     */
    @Column(name = "meta_value", nullable = false, length = 2000)
    private String value;

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
        if (metadataId == null || metadataId.isEmpty()) {
            metadataId = UUID.randomUUID().toString();
            log.debug("Generated new Metadata UUID: {}", metadataId);
        }
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
    }
}
