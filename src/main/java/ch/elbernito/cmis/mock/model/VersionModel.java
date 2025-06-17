package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "versions")
@Getter
@Setter
public class VersionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "document_id", nullable = false)
    private DocumentModel document;

    @Column(nullable = false)
    private String versionLabel;

    @Column(nullable = false)
    private boolean isMajorVersion;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private String comment;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
