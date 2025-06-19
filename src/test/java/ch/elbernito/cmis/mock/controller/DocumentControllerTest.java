package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
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
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        documentRepository.deleteAll();
    }

    @Test
    public void testCreateGetDeleteDocument() throws Exception {
        DocumentDto dto = DocumentDto.builder()
                .name("Test Document")
                .mimeType("text/plain")
                .build();

        // Create
        String response = mockMvc.perform(post("/api/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Document")))
                .andReturn().getResponse().getContentAsString();

        DocumentDto created = objectMapper.readValue(response, DocumentDto.class);

        // Get
        mockMvc.perform(get("/api/documents/" + created.getDocumentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Document")));

        // Delete
        mockMvc.perform(delete("/api/documents/" + created.getDocumentId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUploadAndDownloadContent() throws Exception {
        DocumentDto dto = DocumentDto.builder()
                .name("ContentDoc")
                .mimeType("text/plain")
                .build();

        // Create
        String response = mockMvc.perform(post("/api/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DocumentDto created = objectMapper.readValue(response, DocumentDto.class);

        // Upload content
        byte[] content = "Hello world!".getBytes();
        String uploadContent = "{\"documentId\":\"" + created.getDocumentId() + "\",\"content\":\"" + java.util.Base64.getEncoder().encodeToString(content) + "\",\"mimeType\":\"text/plain\"}";
        mockMvc.perform(post("/api/documents/" + created.getDocumentId() + "/content")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(uploadContent))
                .andExpect(status().isOk());

        // Download content
        mockMvc.perform(get("/api/documents/" + created.getDocumentId() + "/content"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", containsString(created.getDocumentId())));
    }
}
