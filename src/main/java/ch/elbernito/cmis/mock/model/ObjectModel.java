package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * Represents a generic CMIS Object.
 */
@Entity
@Table(name = "cmis_object")
@Data
public class ObjectModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "object_id", nullable = false, unique = true)
    private String objectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modification_date")
    private LocalDateTime lastModificationDate;

    @Column(name = "type_id", nullable = false)
    private String typeId;

    // Weitere optionale Felder/Beziehungen je nach Bedarf...

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
