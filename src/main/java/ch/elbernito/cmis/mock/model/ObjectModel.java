package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.io.Serializable;
import java.util.UUID;

/**
 * Base entity for CMIS objects (Document, Folder, etc.).
 */
@Entity
@Table(name = "cmis_object")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ObjectModel implements Serializable {

    /**
     * Internal numeric database ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * CMIS Object ID (UUID).
     */
    @Column(name = "object_id", nullable = false, unique = true, updatable = false, length = 36)
    private String objectId;

    /**
     * Name of the CMIS object.
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Type (Document, Folder, etc.)
     */
    @Column(nullable = false, length = 50)
    private String type;

    /**
     * Parent folder ID (UUID), nullable for root.
     */
    @Column(name = "parent_folder_id", length = 36)
    private String parentFolderId;

    /**
     * Path (for getObjectByPath), unique.
     */
    @Column(nullable = false, unique = true, length = 1024)
    private String path;

    /**
     * Constructor sets UUID automatically.
     */
    @PrePersist
    public void prePersist() {
        if (objectId == null || objectId.isEmpty()) {
            objectId = UUID.randomUUID().toString();
            log.debug("Generated new CMIS Object UUID: {}", objectId);
        }
    }
}
