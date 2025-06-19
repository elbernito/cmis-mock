package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.TypeDefinitionDto;
import ch.elbernito.cmis.mock.repository.TypeDefinitionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TypeDefinitionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TypeDefinitionRepository typeDefinitionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        typeDefinitionRepository.deleteAll();
    }

    @Test
    public void testCreateGetListTypeDefinition() throws Exception {
        TypeDefinitionDto dto = TypeDefinitionDto.builder()
                .name("cmis:document")
                .description("Standard Document Type")
                .parentTypeId(null)
                .creatable(true)
                .build();

        // Create
        String response = mockMvc.perform(post("/api/types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("cmis:document")))
                .andReturn().getResponse().getContentAsString();

        TypeDefinitionDto created = objectMapper.readValue(response, TypeDefinitionDto.class);

        // Get by id
        mockMvc.perform(get("/api/types/" + created.getTypeId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Standard Document Type")));

        // List all
        mockMvc.perform(get("/api/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("cmis:document")));
    }
}
