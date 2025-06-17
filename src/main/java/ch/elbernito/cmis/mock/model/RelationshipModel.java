package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;

/**
 * Entity for CMIS Relationships between CMIS Objects (Document, Folder etc.).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "relationship")
public class RelationshipModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Source object of the relationship
    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private DocumentModel sourceDocument;

    // Target object of the relationship
    @ManyToOne
    @JoinColumn(name = "target_id", nullable = false)
    private DocumentModel targetDocument;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
