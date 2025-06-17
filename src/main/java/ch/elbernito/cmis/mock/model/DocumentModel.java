package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity representing a CMIS Document according to the CMIS 1.2 specification.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "document")
public class DocumentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "object_id", nullable = false, unique = true)
    private String objectId;

    @Column(name = "content", nullable = true)
    @Lob
    private byte[] content;

    @Column(name = "content_size")
    private long contentSize;

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

    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    private FolderModel parentFolder;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VersionModel> versions;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MetadataModel> metadata;

    @ManyToMany
    @JoinTable(
            name = "document_policy",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "policy_id")
    )
    private List<PolicyModel> policies = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "document_acl",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "acl_id")
    )
    private Set<AclModel> acls = new HashSet<>();

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
