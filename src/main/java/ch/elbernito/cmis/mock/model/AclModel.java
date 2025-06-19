package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing an Access Control List entry for a CMIS object.
 */
@Entity
@Table(name = "acl")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AclModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ACL UUID (external CMIS id).
     */
    @Column(name = "acl_id", nullable = false, unique = true, updatable = false, length = 36)
    private String aclId;

    /**
     * Object ID to which this ACL applies.
     */
    @Column(name = "object_id", nullable = false, length = 36)
    private String objectId;

    /**
     * Principal/user for this ACL entry.
     */
    @Column(nullable = false, length = 255)
    private String principal;

    /**
     * Allowed permissions (comma-separated).
     */
    @Column(name = "permissions", nullable = false, length = 255)
    private String permissions;

    /**
     * Creation time.
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @PrePersist
    public void prePersist() {
        if (aclId == null || aclId.isEmpty()) {
            aclId = UUID.randomUUID().toString();
            log.debug("Generated new ACL UUID: {}", aclId);
        }
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
    }
}
