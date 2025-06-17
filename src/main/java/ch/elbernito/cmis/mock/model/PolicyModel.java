package ch.elbernito.cmis.mock.model;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cmis_policy")
public class PolicyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String policyId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String policyText;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private String objectId; // Zu welchem Objekt gehört diese Policy


    // Kein CMIS Standard, aber gut zu wissen
    @ManyToMany(mappedBy = "policies")
    private List<DocumentModel> documents = new ArrayList<>();

    @ManyToMany(mappedBy = "policies")
    private List<FolderModel> folders = new ArrayList<>();

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
