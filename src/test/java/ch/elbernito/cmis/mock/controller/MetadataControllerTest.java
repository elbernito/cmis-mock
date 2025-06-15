package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.MetadataDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for MetadataController
 */
@SpringBootTest
@AutoConfigureMockMvc
class MetadataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateGetUpdateDeleteMetadata() throws Exception {
        // Arrange
        MetadataDto dto = new MetadataDto();
        dto.setObjectId("obj-123");
        dto.setPropertyName("author");
        dto.setPropertyValue("Alice");

        // Act: create
        String json = mockMvc.perform(post("/api/metadata")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.propertyName", is("author")))
                .andReturn().getResponse().getContentAsString();
        MetadataDto created = objectMapper.readValue(json, MetadataDto.class);

        // Assert: get
        mockMvc.perform(get("/api/metadata/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.propertyValue", is("Alice")));

        // Act: update
        created.setPropertyValue("Bob");
        mockMvc.perform(put("/api/metadata/{id}", created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.propertyValue", is("Bob")));

        // Act & Assert: delete
        mockMvc.perform(delete("/api/metadata/{id}", created.getId()))
                .andExpect(status().isNoContent());

        // Assert: not found
        mockMvc.perform(get("/api/metadata/{id}", created.getId()))
                .andExpect(status().isNotFound());
    }
}
