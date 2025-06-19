package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a CMIS Policy.
 */
@Entity
@Table(name = "policy")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class PolicyModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Policy UUID (external CMIS id).
     */
    @Column(name = "policy_id", nullable = false, unique = true, updatable = false, length = 36)
    private String policyId;

    /**
     * Policy name.
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Policy description.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Policy content (rules/serialized JSON/statement).
     */
    @Column(length = 2000)
    private String content;

    /**
     * Creation time.
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @PrePersist
    public void prePersist() {
        if (policyId == null || policyId.isEmpty()) {
            policyId = UUID.randomUUID().toString();
            log.debug("Generated new Policy UUID: {}", policyId);
        }
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
    }
}
