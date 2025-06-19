package ch.elbernito.cmis.mock.cmis.controller;

import ch.elbernito.cmis.mock.dto.RelationshipDto;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.model.RelationshipModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.RelationshipRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CmisV12RelationshipControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private DocumentModel sourceObj;
    private DocumentModel targetObj;
    private RelationshipModel rel1;
    private RelationshipModel rel2;

    @BeforeEach
    void setup() {
        relationshipRepository.deleteAll();
        documentRepository.deleteAll();

        // Zwei Objekte (z.B. Dokumente) für die Beziehung anlegen
        sourceObj = new DocumentModel();
        sourceObj.setDocumentId("source-1");
        sourceObj.setName("Source Document");
        sourceObj.setMimeType("text/plain");
        sourceObj.setCreatedAt(LocalDateTime.now());
        // weitere Pflichtfelder falls nötig ...
        documentRepository.save(sourceObj);

        targetObj = new DocumentModel();
        targetObj.setDocumentId("target-1");
        targetObj.setName("Target Document");
        targetObj.setMimeType("text/plain");
        targetObj.setCreatedAt(LocalDateTime.now());
        documentRepository.save(targetObj);

        // Zwei Beziehungen anlegen
        rel1 = RelationshipModel.builder()
                .relationshipId(UUID.randomUUID().toString())
                .sourceId("source-1")
                .targetId("target-1")
                .relationshipType("references")
                .creationDate(LocalDateTime.now())
                .build();
        relationshipRepository.save(rel1);

        rel2 = RelationshipModel.builder()
                .relationshipId(UUID.randomUUID().toString())
                .sourceId("source-1")
                .targetId("target-1")
                .relationshipType("parent-child")
                .creationDate(LocalDateTime.now())
                .build();
        relationshipRepository.save(rel2);
    }

    @Test
    void testWhenRelationshipIdExistsThenReturnRelationship() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/relationships/{relationshipId}", rel1.getRelationshipId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.relationshipId").value(rel1.getRelationshipId()))
                .andExpect(jsonPath("$.sourceId").value("source-1"))
                .andExpect(jsonPath("$.targetId").value("target-1"))
                .andExpect(jsonPath("$.relationshipType").value("references"));
    }

    @Test
    void testWhenRelationshipIdDoesNotExistThenReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/relationships/{relationshipId}", "does-not-exist-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testWhenCreatingRelationshipThenReturnCreatedRelationship() throws Exception {
        RelationshipDto newRel = new RelationshipDto();
        newRel.setSourceId("source-1");
        newRel.setTargetId("target-1");
        newRel.setRelationshipType("dependency");

        mockMvc.perform(post("/api/cmis/v1.2/relationships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sourceId").value("source-1"))
                .andExpect(jsonPath("$.targetId").value("target-1"))
                .andExpect(jsonPath("$.relationshipType").value("dependency"))
                .andExpect(jsonPath("$.relationshipId").isNotEmpty());
    }

    @Test
    void testWhenDeletingRelationshipThenRelationshipIsRemoved() throws Exception {
        mockMvc.perform(delete("/api/cmis/v1.2/relationships/{relationshipId}", rel2.getRelationshipId()))
                .andExpect(status().isOk());

        Optional<RelationshipModel> deleted = relationshipRepository.findByRelationshipId(rel2.getRelationshipId());
        assertThat(deleted).isEmpty();
    }

    @Test
    void testWhenGettingAllRelationshipsForObjectThenReturnAll() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/objects/{objectId}/relationships/all", "source-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.relationshipId == '%s')]", rel1.getRelationshipId()).exists())
                .andExpect(jsonPath("$[?(@.relationshipId == '%s')]", rel2.getRelationshipId()).exists());
    }
}
