package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.RetentionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for RetentionController
 */
@SpringBootTest
@AutoConfigureMockMvc
class RetentionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateGetDeleteRetention() throws Exception {
        // Arrange
        RetentionDto dto = new RetentionDto();
        dto.setObjectId("obj-1");
        dto.setRetentionPolicyName("retainX");
        dto.setRetainUntil(Instant.parse("2025-12-31T23:59:59Z"));

        // Act: create
        String json = mockMvc.perform(post("/api/retention")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.retentionPolicyName", is("retainX")))
                .andReturn().getResponse().getContentAsString();
        RetentionDto created = objectMapper.readValue(json, RetentionDto.class);

        // Assert: get
        mockMvc.perform(get("/api/retention/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objectId", is("obj-1")));

        // Act & Assert: delete
        mockMvc.perform(delete("/api/retention/{id}", created.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/retention/{id}", created.getId()))
                .andExpect(status().isNotFound());
    }
}
