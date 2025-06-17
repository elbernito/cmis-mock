package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "retentions")
public class RetentionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String retentionId;

    @Column(nullable = false)
    private String objectId; // CMIS objectId, referenziert Document, Folder, etc.

    @Column(nullable = false)
    private LocalDateTime retentionStart;

    @Column(nullable = false)
    private LocalDateTime retentionEnd;

    private String description;

    @Column(nullable = false)
    private boolean active;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
