package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing a CMIS Folder.
 */
@Entity
@Table(name = "folder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class FolderModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Folder UUID (external CMIS id).
     */
    @Column(name = "folder_id", nullable = false, unique = true, updatable = false, length = 36)
    private String folderId;

    /**
     * CMIS Object ID reference (for relationships).
     */
    @Column(name = "object_id", nullable = false, unique = true, updatable = false, length = 36)
    private String objectId;

    /**
     * Name of the folder.
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Parent folder (bidirectional, nullable for root).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_folder_id")
    private FolderModel parentFolder;

    /**
     * List of child folders.
     */
    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<FolderModel> children = new ArrayList<>();

    /**
     * Creation date.
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    /**
     * Last modified date.
     */
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    /**
     * Set UUIDs and timestamps automatically.
     */
    @PrePersist
    public void prePersist() {
        if (folderId == null || folderId.isEmpty()) {
            folderId = UUID.randomUUID().toString();
            log.debug("Generated new Folder UUID: {}", folderId);
        }
        if (objectId == null || objectId.isEmpty()) {
            objectId = UUID.randomUUID().toString();
            log.debug("Generated new Object UUID for Folder: {}", objectId);
        }
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
        if (lastModifiedDate == null) {
            lastModifiedDate = creationDate;
        }
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedDate = LocalDateTime.now();
    }
}
