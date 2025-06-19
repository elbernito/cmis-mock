package ch.elbernito.cmis.mock.example;

import ch.elbernito.cmis.mock.model.TypeDefinitionModel;
import ch.elbernito.cmis.mock.repository.TypeDefinitionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Loads standardized CMIS TypeDefinitions on application startup.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TypeDefinitionInitializer {

    private final TypeDefinitionRepository typeDefinitionRepository;

    @PostConstruct
    public void initTypeDefinitions() {
        log.info("Loading default CMIS TypeDefinitions...");

        // Standardisierte Typen nach CMIS 1.1/1.2
        List<TypeDefinitionModel> defaultTypes = List.of(
                TypeDefinitionModel.builder()
                        .typeId("cmis:document")
                        .name("Document")
                        .description("CMIS Base Document Type")
                        .parentTypeId(null)
                        .creatable(true)
                        .build(),
                TypeDefinitionModel.builder()
                        .typeId("cmis:folder")
                        .name("Folder")
                        .description("CMIS Base Folder Type")
                        .parentTypeId(null)
                        .creatable(true)
                        .build(),
                TypeDefinitionModel.builder()
                        .typeId("cmis:relationship")
                        .name("Relationship")
                        .description("CMIS Relationship Type")
                        .parentTypeId(null)
                        .creatable(false)
                        .build(),
                TypeDefinitionModel.builder()
                        .typeId("cmis:policy")
                        .name("Policy")
                        .description("CMIS Policy Type")
                        .parentTypeId(null)
                        .creatable(true)
                        .build(),
                TypeDefinitionModel.builder()
                        .typeId("cmis:item")
                        .name("Item")
                        .description("CMIS Item Type")
                        .parentTypeId(null)
                        .creatable(true)
                        .build()
        );

        for (TypeDefinitionModel type : defaultTypes) {
            typeDefinitionRepository.findByTypeId(type.getTypeId())
                .ifPresentOrElse(
                    existing -> log.debug("TypeDefinition '{}' already exists, skipping.", type.getTypeId()),
                    () -> {
                        typeDefinitionRepository.save(type);
                        log.info("TypeDefinition '{}' created.", type.getTypeId());
                    }
                );
        }

        log.info("CMIS TypeDefinitions loading finished.");
    }
}
