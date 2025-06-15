package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for DocumentController
 */
@SpringBootTest
@AutoConfigureMockMvc
class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUploadGetDownloadAndDelete() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes());

        // Act: upload
        String json = mockMvc.perform(multipart("/api/documents")
                        .file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("hello.txt")))
                .andReturn()
                .getResponse()
                .getContentAsString();
        DocumentDto dto = objectMapper.readValue(json, DocumentDto.class);

        // Assert: get metadata
        mockMvc.perform(get("/api/documents/{id}", dto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId())));

        // Assert: download content
        mockMvc.perform(get("/api/documents/{id}/download", dto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().bytes("Hello World".getBytes()));

        // Act & Assert: delete
        mockMvc.perform(delete("/api/documents/{id}", dto.getId()))
                .andExpect(status().isNoContent());

        // Assert: no longer exists
        mockMvc.perform(get("/api/documents/{id}", dto.getId()))
                .andExpect(status().isNotFound());
    }
}
