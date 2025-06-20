package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "policy_assignment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolicyAssignmentModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "object_id", nullable = false, length = 36)
    private String objectId;

    @Column(name = "policy_id", nullable = false, length = 36)
    private String policyId;
}
