package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.MetadataDto;
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
 * Integration test for MetadataController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class MetadataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetMetadata() throws Exception {
        MetadataDto metadata = MetadataDto.builder()
                .propertyKey("meta-test-key")
                .propertyValue("TestValue")
                .createdBy("tester")
                .createdAt(LocalDateTime.now())
                .build();

        // Create
        mockMvc.perform(post("/api/metadata")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metadata)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.propertyKey").value("meta-test-key"))
                .andExpect(jsonPath("$.propertyValue").value("TestValue"));

        // Get all
        mockMvc.perform(get("/api/metadata"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteMetadataNotFound() throws Exception {
        mockMvc.perform(delete("/api/metadata/99999"))
                .andExpect(status().isNotFound());
    }
}
