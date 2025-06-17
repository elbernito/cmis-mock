package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.TypeDefinitionDto;
import ch.elbernito.cmis.mock.repository.TypeDefinitionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TypeDefinitionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TypeDefinitionRepository repository;

    @BeforeEach
    void clearRepo() {
        repository.deleteAll();
    }

    @Test
    void testCreateAndGetTypeDefinition() throws Exception {
        TypeDefinitionDto dto = new TypeDefinitionDto();
        dto.setTypeId("cmis:document");
        dto.setDisplayName("Document");
        dto.setBaseTypeId("cmis:document");
        dto.setDescription("Standard CMIS Document");

        // Create
        String response = mockMvc.perform(post("/api/type-definitions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.typeId").value("cmis:document"))
                .andReturn()
                .getResponse().getContentAsString();

        TypeDefinitionDto created = objectMapper.readValue(response, TypeDefinitionDto.class);

        // Get
        mockMvc.perform(get("/api/type-definitions/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("Document"));
    }

    @Test
    void testUpdateTypeDefinition() throws Exception {
        TypeDefinitionDto dto = new TypeDefinitionDto();
        dto.setTypeId("cmis:folder");
        dto.setDisplayName("Folder");
        dto.setBaseTypeId("cmis:folder");
        dto.setDescription("Standard Folder");

        TypeDefinitionDto saved = objectMapper.readValue(
                mockMvc.perform(post("/api/type-definitions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                        .andReturn()
                        .getResponse().getContentAsString(),
                TypeDefinitionDto.class);

        saved.setDisplayName("Changed Folder");
        mockMvc.perform(put("/api/type-definitions/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("Changed Folder"));
    }

    @Test
    void testGetAllAndDeleteTypeDefinition() throws Exception {
        TypeDefinitionDto dto1 = new TypeDefinitionDto();
        dto1.setTypeId("cmis:policy");
        dto1.setDisplayName("Policy");
        dto1.setBaseTypeId("cmis:policy");
        dto1.setDescription("Policy Desc");
        mockMvc.perform(post("/api/type-definitions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto1)))
                .andExpect(status().isOk());

        TypeDefinitionDto dto2 = new TypeDefinitionDto();
        dto2.setTypeId("cmis:item");
        dto2.setDisplayName("Item");
        dto2.setBaseTypeId("cmis:item");
        dto2.setDescription("Item Desc");
        mockMvc.perform(post("/api/type-definitions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto2)))
                .andExpect(status().isOk());

        // Get all
        String resp = mockMvc.perform(get("/api/type-definitions"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(resp).contains("Policy", "Item");

        // Delete
        Long id = repository.findAll().getFirst().getId();
        mockMvc.perform(delete("/api/type-definitions/" + id))
                .andExpect(status().isNoContent());

        assertThat(repository.existsById(id)).isFalse();
    }
}
