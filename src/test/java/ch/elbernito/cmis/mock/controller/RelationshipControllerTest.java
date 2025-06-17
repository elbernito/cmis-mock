package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.dto.RelationshipDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for RelationshipController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class RelationshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long sourceDocumentId;
    private Long targetDocumentId;

    @BeforeEach
    void setup() throws Exception {
        // Ensure two Documents exist for relationships
        DocumentDto doc1 = DocumentDto.builder()
                .name("SourceDoc")
                .createdBy("reltest")
                .lastModifiedBy("reltest")
                .objectId(UUID.randomUUID().toString())
                .typeId(UUID.randomUUID().toString())
                .lastModifiedDate(LocalDateTime.now())
                .creationDate(LocalDateTime.now())
                .build();

        DocumentDto doc2 = DocumentDto.builder()
                .name("TargetDoc")
                .createdBy("reltest")
                .lastModifiedBy("reltest")
                .objectId(UUID.randomUUID().toString())
                .typeId(UUID.randomUUID().toString())
                .lastModifiedDate(LocalDateTime.now())
                .creationDate(LocalDateTime.now())
                .build();

        String response1 = mockMvc.perform(post("/api/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doc1)))
                .andReturn().getResponse().getContentAsString();

        String response2 = mockMvc.perform(post("/api/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doc2)))
                .andReturn().getResponse().getContentAsString();

        sourceDocumentId = objectMapper.readTree(response1).get("id").asLong();
        targetDocumentId = objectMapper.readTree(response2).get("id").asLong();
    }

    @Test
    void testCreateAndGetRelationship() throws Exception {
        RelationshipDto relationship = RelationshipDto.builder()
                .sourceDocumentId(sourceDocumentId)
                .targetDocumentId(targetDocumentId)
                .type("references")
                .createdBy("reltest")
                .createdAt(LocalDateTime.now())
                .build();

        // Create
        mockMvc.perform(post("/api/relationships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relationship)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("references"));

        // Get all
        mockMvc.perform(get("/api/relationships"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteRelationshipNotFound() throws Exception {
        mockMvc.perform(delete("/api/relationships/99999"))
                .andExpect(status().isNotFound());
    }
}
