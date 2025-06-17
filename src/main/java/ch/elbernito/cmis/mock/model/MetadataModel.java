package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;

/**
 * Entity representing Metadata (CMIS Property) for Documents and Folders.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "metadata")
public class MetadataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "property_key", nullable = false)
    private String propertyKey;

    @Column(name = "property_value", nullable = false)
    private String propertyValue;

    /**
     * The CMIS object (Document or Folder) this metadata belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "document_id")
    private DocumentModel document;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private FolderModel folder;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
