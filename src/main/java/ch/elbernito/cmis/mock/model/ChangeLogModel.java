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
 * Entity representing a CMIS ChangeLog entry.
 */
@Entity
@Table(name = "changelog")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ChangeLogModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ChangeLog UUID (external CMIS id).
     */
    @Column(name = "changelog_id", nullable = false, unique = true, updatable = false, length = 36)
    private String changelogId;

    /**
     * Object affected by this change.
     */
    @Column(name = "object_id", nullable = false, length = 36)
    private String objectId;

    /**
     * Type of change (CREATE, UPDATE, DELETE, ...).
     */
    @Column(name = "change_type", nullable = false, length = 30)
    private String changeType;

    /**
     * Human-readable summary.
     */
    @Column(name = "summary", length = 512)
    private String summary;

    /**
     * Date/time of change.
     */
    @Column(name = "change_time")
    private LocalDateTime changeTime;

    @PrePersist
    public void prePersist() {
        if (changelogId == null || changelogId.isEmpty()) {
            changelogId = UUID.randomUUID().toString();
            log.debug("Generated new ChangeLog UUID: {}", changelogId);
        }
        if (changeTime == null) {
            changeTime = LocalDateTime.now();
        }
    }
}
