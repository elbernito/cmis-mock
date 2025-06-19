package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a CMIS Document.
 */
@Entity
@Table(name = "document")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class DocumentModel implements Serializable {

    /**
     * Internal numeric database ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Document unique identifier (UUID).
     */
    @Column(name = "document_id", nullable = false, unique = true, updatable = false, length = 36)
    private String documentId;

    /**
     * Document name.
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Description or title.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Mime type (e.g., application/pdf).
     */
    @Column(length = 255)
    private String mimeType;

    /**
     * The binary file content (as blob).
     */
    @Lob
    @Column(name = "content")
    private byte[] content;

    /**
     * Content length (bytes).
     */
    @Column(name = "content_length")
    private Long contentLength;

    /**
     * The current version label.
     */
    @Column(name = "version_label", length = 50)
    private String versionLabel;

    /**
     * Indicates if this is the latest version.
     */
    @Column(name = "is_latest_version")
    private Boolean isLatestVersion;

    /**
     * Last modified date.
     */
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    /**
     * Creation date.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Parent folder (UUID, can be null).
     */
    @Column(name = "parent_folder_id", length = 36)
    private String parentFolderId;


    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    /**
     * Link to generic CMIS object (for API compatibility).
     */
    @Column(name = "object_id", nullable = false, length = 36, unique = true)
    private String objectId;

    /**
     * Document type (for extensibility).
     */
    @Column(name = "type_id", length = 50)
    private String typeId;

    /**
     * On create: set UUIDs and dates if not present.
     */
    @PrePersist
    public void prePersist() {
        if (documentId == null || documentId.isEmpty()) {
            documentId = UUID.randomUUID().toString();
            log.debug("Generated new Document UUID: {}", documentId);
        }
        if (objectId == null || objectId.isEmpty()) {
            objectId = UUID.randomUUID().toString();
            log.debug("Generated new Object UUID for Document: {}", objectId);
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        lastModifiedAt = LocalDateTime.now();
        if (isLatestVersion == null) {
            isLatestVersion = true;
        }
        if (versionLabel == null) {
            versionLabel = "1.0";
        }
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedAt = LocalDateTime.now();
    }


}
