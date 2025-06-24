package ch.elbernito.cmis.mock.example;

import ch.elbernito.cmis.mock.model.TypeDefinitionModel;
import ch.elbernito.cmis.mock.repository.TypeDefinitionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Loads standardized CMIS TypeDefinitions on application startup.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TypeDefinitionInitializer {

    private final TypeDefinitionRepository typeDefinitionRepository;

    @PostConstruct
    public void initializeCmisTypes() {
        createIfNotExists("cmis:document", "Document", "CMIS Base Document Type", true);
        createIfNotExists("cmis:folder", "Folder", "CMIS Base Folder Type", true);
        createIfNotExists("cmis:relationship", "Relationship", "CMIS Relationship Type", false);
        createIfNotExists("cmis:policy", "Policy", "CMIS Policy Type", true);
        createIfNotExists("cmis:item", "Item", "CMIS Item Type", true);
        createIfNotExists("cmis:secondary", "Secondary", "CMIS Secondary Type", false);
        log.info("CMIS TypeDefinitions loading finished.");
    }

    private void createIfNotExists(String typeId, String name, String description, Boolean creatable) {
        if (typeDefinitionRepository.findByTypeId(typeId).isEmpty()) {
            TypeDefinitionModel model = TypeDefinitionModel.builder()
                    .typeId(typeId)
                    .name(name)
                    .description(description)
                    .parentTypeId(null)
                    .creatable(creatable)
                    .build();
            typeDefinitionRepository.save(model);
        }
    }



}
