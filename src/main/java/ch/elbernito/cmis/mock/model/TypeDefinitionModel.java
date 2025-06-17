package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(name = "type_definitions")
@Data
public class TypeDefinitionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String typeId;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String baseTypeId;

    private String description;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
