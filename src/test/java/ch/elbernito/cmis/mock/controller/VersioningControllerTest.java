package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.VersionDto;
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
 * Integration tests for VersioningController
 */
@SpringBootTest
@AutoConfigureMockMvc
class VersioningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateListGetDeleteVersion() throws Exception {
        // Arrange
        VersionDto dto = new VersionDto();
        dto.setObjectId("obj-123");
        dto.setVersionLabel("v1.0");
        dto.setLatest(true);

        // Act: create
        String json = mockMvc.perform(post("/api/versions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.versionLabel", is("v1.0")))
                .andReturn().getResponse().getContentAsString();
        VersionDto created = objectMapper.readValue(json, VersionDto.class);

        // Assert: list
        mockMvc.perform(get("/api/versions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", hasItem(created.getId())));

        // Assert: get
        mockMvc.perform(get("/api/versions/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latest", is(true)));

        // Act & Assert: delete
        mockMvc.perform(delete("/api/versions/{id}", created.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/versions/{id}", created.getId()))
                .andExpect(status().isNotFound());
    }
}
