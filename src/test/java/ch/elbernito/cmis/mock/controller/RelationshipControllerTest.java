package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.RelationshipDto;
import ch.elbernito.cmis.mock.repository.RelationshipRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RelationshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        relationshipRepository.deleteAll();
    }

    @Test
    public void testCreateGetDeleteRelationship() throws Exception {
        RelationshipDto dto = RelationshipDto.builder()
                .sourceId("obj-1")
                .targetId("obj-2")
                .relationshipType("reltype")
                .build();

        // Create
        String response = mockMvc.perform(post("/api/crud/relationships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.relationshipType", is("reltype")))
                .andReturn().getResponse().getContentAsString();

        RelationshipDto created = objectMapper.readValue(response, RelationshipDto.class);

        // Get
        mockMvc.perform(get("/api/crud/relationships/" + created.getRelationshipId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sourceId", is("obj-1")));

        // List by object
        mockMvc.perform(get("/api/crud/relationships/object/obj-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].targetId", is("obj-2")));

        // Delete
        mockMvc.perform(delete("/api/crud/relationships/" + created.getRelationshipId()))
                .andExpect(status().isNoContent());
    }
}
