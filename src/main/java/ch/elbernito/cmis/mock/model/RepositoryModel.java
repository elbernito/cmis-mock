package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.io.Serializable;
import java.util.UUID;

/**
 * Entity representing a CMIS Repository.
 * Contains all basic repository properties.
 */
@Entity
@Table(name = "repository")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class RepositoryModel implements Serializable {

    /**
     * Internal numeric database ID (autoincrement).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Globally unique Repository ID (UUID).
     */
    @Column(name = "repository_id", nullable = false, unique = true, updatable = false, length = 36)
    private String repositoryId;

    /**
     * Name of the repository.
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Human-readable description of the repository.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Root folder ID of the repository.
     */
    @Column(name = "root_folder_id", nullable = false, length = 36)
    private String rootFolderId;

    /**
     * Capabilities info (serialized as JSON/text, for simplicity).
     */
    @Column(length = 2000)
    private String capabilities;

    /**
     * Constructor setting UUID automatically.
     */
    @PrePersist
    public void prePersist() {
        if (repositoryId == null || repositoryId.isEmpty()) {
            repositoryId = UUID.randomUUID().toString();
            log.debug("Generated new Repository UUID: {}", repositoryId);
        }
        if (rootFolderId == null || rootFolderId.isEmpty()) {
            rootFolderId = UUID.randomUUID().toString();
            log.debug("Generated new Root Folder UUID: {}", rootFolderId);
        }
    }
}
