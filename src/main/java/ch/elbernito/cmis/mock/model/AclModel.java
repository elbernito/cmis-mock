package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class AclModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String principal;

    @Column(nullable = false)
    private String permission;

    @ManyToMany(mappedBy = "acls")
    private Set<DocumentModel> documents = new HashSet<>();

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
