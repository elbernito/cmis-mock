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
 * Entity representing a retention entry for a CMIS object.
 */
@Entity
@Table(name = "retention")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class RetentionModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Retention UUID (external CMIS id).
     */
    @Column(name = "retention_id", nullable = false, unique = true, updatable = false, length = 36)
    private String retentionId;

    /**
     * Object ID to which this retention applies.
     */
    @Column(name = "object_id", nullable = false, length = 36)
    private String objectId;

    /**
     * Retention policy label.
     */
    @Column(name = "label", length = 255)
    private String label;

    /**
     * Retention until date (end of retention period).
     */
    @Column(name = "retention_until")
    private LocalDateTime retentionUntil;

    /**
     * Creation time.
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @PrePersist
    public void prePersist() {
        if (retentionId == null || retentionId.isEmpty()) {
            retentionId = UUID.randomUUID().toString();
            log.debug("Generated new Retention UUID: {}", retentionId);
        }
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
    }
}
