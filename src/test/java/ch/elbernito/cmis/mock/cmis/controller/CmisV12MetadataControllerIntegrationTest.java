package ch.elbernito.cmis.mock.cmis.controller;

import ch.elbernito.cmis.mock.dto.MetadataDto;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.model.MetadataModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.MetadataRepository;
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
class CmisV12MetadataControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MetadataRepository metadataRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MetadataModel meta1;
    private MetadataModel meta2;

    @BeforeEach
    void setup() {
        documentRepository.deleteAll();
        metadataRepository.deleteAll();

        // Erstes Metadata anlegen
        meta1 = MetadataModel.builder()
                .metadataId(UUID.randomUUID().toString())
                .documentId("doc-1")
                .key("author")
                .value("Max Tester")
                .creationDate(LocalDateTime.now())
                .build();
        metadataRepository.save(meta1);

        // Zweites Metadata anlegen
        meta2 = MetadataModel.builder()
                .metadataId(UUID.randomUUID().toString())
                .documentId("doc-1")
                .key("version")
                .value("1.0")
                .creationDate(LocalDateTime.now())
                .build();
        metadataRepository.save(meta2);

        // Dokument "doc-1" für den Test anlegen
        DocumentModel doc1 = new DocumentModel();
        doc1.setDocumentId("doc-1");
        doc1.setName("Testdokument");
        documentRepository.save(doc1);

        // Dokument "doc-2" für den Test anlegen
        DocumentModel doc2 = new DocumentModel();
        doc2.setDocumentId("doc-2");
        doc2.setName("Testdokument");
        documentRepository.save(doc2);
    }

    @Test
    void testWhenMetadataIdExistsThenReturnMetadata() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/metadata/{metadataId}", meta1.getMetadataId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadataId").value(meta1.getMetadataId()))
                .andExpect(jsonPath("$.key").value("author"))
                .andExpect(jsonPath("$.value").value("Max Tester"));
    }

    @Test
    void testWhenMetadataIdDoesNotExistThenReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/metadata/{metadataId}", "does-not-exist-id"))
                .andExpect(status().isNotFound());
                // Optional: .andExpect(jsonPath("$.message").value("Metadata not found: does-not-exist-id"));
    }

    @Test
    void testWhenCreatingMetadataThenReturnCreatedMetadata() throws Exception {
        MetadataDto newMeta = new MetadataDto();
        newMeta.setDocumentId("doc-2");
        newMeta.setKey("subject");
        newMeta.setValue("IntegrationTest");

        mockMvc.perform(post("/api/cmis/v1.2/metadata")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMeta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documentId").value("doc-2"))
                .andExpect(jsonPath("$.key").value("subject"))
                .andExpect(jsonPath("$.value").value("IntegrationTest"))
                .andExpect(jsonPath("$.metadataId").isNotEmpty());
    }

    @Test
    void testWhenUpdatingMetadataThenReturnUpdatedMetadata() throws Exception {
        MetadataDto updateMeta = new MetadataDto();
        updateMeta.setMetadataId(meta1.getMetadataId());
        updateMeta.setDocumentId(meta1.getDocumentId());
        updateMeta.setKey(meta1.getKey());
        updateMeta.setValue("CHANGED");

        mockMvc.perform(put("/api/cmis/v1.2/metadata/{metadataId}", meta1.getMetadataId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateMeta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadataId").value(meta1.getMetadataId()))
                .andExpect(jsonPath("$.value").value("CHANGED"));
    }

    @Test
    void testWhenDeletingMetadataThenMetadataIsRemoved() throws Exception {
        mockMvc.perform(delete("/api/cmis/v1.2/metadata/{metadataId}", meta2.getMetadataId()))
                .andExpect(status().isOk());

        Optional<MetadataModel> deleted = metadataRepository.findByMetadataId(meta2.getMetadataId());
        assertThat(deleted).isEmpty();
    }

    @Test
    void testWhenGettingAllMetadataForDocumentThenReturnAll() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/documents/{documentId}/metadata", "doc-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.metadataId == '%s')]", meta1.getMetadataId()).exists())
                .andExpect(jsonPath("$[?(@.metadataId == '%s')]", meta2.getMetadataId()).exists());
    }
}
