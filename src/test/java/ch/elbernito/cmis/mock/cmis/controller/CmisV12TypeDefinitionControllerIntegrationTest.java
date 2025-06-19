package ch.elbernito.cmis.mock.cmis.controller;

import ch.elbernito.cmis.mock.model.TypeDefinitionModel;
import ch.elbernito.cmis.mock.repository.TypeDefinitionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CmisV12TypeDefinitionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TypeDefinitionRepository typeDefinitionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private TypeDefinitionModel rootType;
    private TypeDefinitionModel subType;

    @BeforeEach
    void setup() {
        typeDefinitionRepository.deleteAll();

        rootType = TypeDefinitionModel.builder()
                .typeId(UUID.randomUUID().toString())
                .name("RootType")
                .description("The root type definition")
                .parentTypeId(null)
                .creatable(true)
                .build();
        typeDefinitionRepository.save(rootType);

        subType = TypeDefinitionModel.builder()
                .typeId(UUID.randomUUID().toString())
                .name("SubType")
                .description("A sub type")
                .parentTypeId(rootType.getTypeId())
                .creatable(false)
                .build();
        typeDefinitionRepository.save(subType);
    }

    @Test
    void testWhenGettingAllTypeDefinitionsThenReturnList() throws Exception {
        // Der API-Aufruf gibt laut Controller ALLE Typen zurück, der Parameter wird ignoriert!
        mockMvc.perform(get("/api/cmis/v1.2/types/children")
                        .param("typeId", rootType.getTypeId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.typeId == '%s')]", rootType.getTypeId()).exists())
                .andExpect(jsonPath("$[?(@.typeId == '%s')]", subType.getTypeId()).exists());
    }

    @Test
    void testWhenTypeIdExistsThenReturnTypeDefinition() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/types/{typeId}", rootType.getTypeId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.typeId").value(rootType.getTypeId()))
                .andExpect(jsonPath("$.name").value("RootType"))
                .andExpect(jsonPath("$.creatable").value(true));
    }

    @Test
    void testWhenTypeIdDoesNotExistThenReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/types/{typeId}", "does-not-exist"))
                .andExpect(status().isNotFound());
    }
}
