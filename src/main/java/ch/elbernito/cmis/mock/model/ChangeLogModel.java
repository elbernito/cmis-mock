package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;

@Entity
@Data
public class ChangeLogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String objectId;

    @Column(nullable = false)
    private String changeType; // z.B. "CREATED", "UPDATED", "DELETED"

    @Column(nullable = false)
    private String changedBy;

    @Column(nullable = false)
    private LocalDateTime changeTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
