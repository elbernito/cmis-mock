package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a CMIS Folder according to the CMIS 1.2 specification.
 * Contains all basic properties and relationships as required by the specification.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "folder")
public class FolderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "object_id", nullable = false, unique = true)
    private String objectId;

    @Column(nullable = false)
    private String name;

    @Column(name = "type_id", nullable = false)
    private String typeId;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate;

    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    // Parent-Ordner (optional, für Hierarchie)
    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    private FolderModel parentFolder;

    // Unterordner
    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL)
    private List<FolderModel> children = new ArrayList<>();

    // Dokumente im Ordner
    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL)
    private List<DocumentModel> documents = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "folder_policy",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "policy_id")
    )
    private List<PolicyModel> policies = new ArrayList<>();

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
