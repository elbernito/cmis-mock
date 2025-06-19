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
 * Entity representing a CMIS Document Version.
 */
@Entity
@Table(name = "version")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class VersionModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Version UUID (external CMIS id).
     */
    @Column(name = "version_id", nullable = false, unique = true, updatable = false, length = 36)
    private String versionId;

    /**
     * Associated document (by objectId).
     */
    @Column(name = "object_id", nullable = false, length = 36)
    private String objectId;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private DocumentModel document;

    /**
     * Version label.
     */
    @Column(name = "version_label", length = 100)
    private String versionLabel;

    /**
     * Is this the latest version?
     */
    @Column(name = "is_latest_version")
    private Boolean isLatestVersion;

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
        if (versionId == null || versionId.isEmpty()) {
            versionId = UUID.randomUUID().toString();
            log.debug("Generated new Version UUID: {}", versionId);
        }
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
        if (isLatestVersion == null) {
            isLatestVersion = true;
        }
    }
}
