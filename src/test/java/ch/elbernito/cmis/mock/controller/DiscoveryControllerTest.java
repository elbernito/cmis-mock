package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.DiscoveryDto;
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
 * Integration tests for DiscoveryController
 */
@SpringBootTest
@AutoConfigureMockMvc
class DiscoveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateListGetDeleteDiscovery() throws Exception {
        // Arrange
        DiscoveryDto dto = new DiscoveryDto();
        dto.setQuery("SELECT *");
        dto.setDescription("All objects");

        // Act: create
        String json = mockMvc.perform(post("/api/discovery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.query", is("SELECT *")))
                .andReturn().getResponse().getContentAsString();
        DiscoveryDto created = objectMapper.readValue(json, DiscoveryDto.class);

        // Assert: list
        mockMvc.perform(get("/api/discovery"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", hasItem(created.getId())));

        // Assert: get
        mockMvc.perform(get("/api/discovery/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("All objects")));

        // Act & Assert: delete
        mockMvc.perform(delete("/api/discovery/{id}", created.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/discovery/{id}", created.getId()))
                .andExpect(status().isNotFound());
    }
}
