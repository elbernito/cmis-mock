package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for DocumentController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetDocument() throws Exception {
        DocumentDto doc = DocumentDto.builder()
                .objectId("doc-test-001")
                .name("Test Document")
                .typeId("cmis:document")
                .creationDate(LocalDateTime.now())
                .createdBy("tester")
                .lastModifiedDate(LocalDateTime.now())
                .lastModifiedBy("tester")
                .build();

        // Create
        mockMvc.perform(post("/api/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objectId").value("doc-test-001"))
                .andExpect(jsonPath("$.name").value("Test Document"));

        // Get by objectId
        mockMvc.perform(get("/api/documents/object/doc-test-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Document"));
    }

    @Test
    void testGetAllDocuments() throws Exception {
        mockMvc.perform(get("/api/documents"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteDocumentNotFound() throws Exception {
        mockMvc.perform(delete("/api/documents/99999"))
                .andExpect(status().isNotFound());
    }
}
