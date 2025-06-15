package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.PolicyDto;
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
 * Integration tests for PolicyController
 */
@SpringBootTest
@AutoConfigureMockMvc
class PolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateGetUpdateDeletePolicy() throws Exception {
        // Arrange
        PolicyDto dto = new PolicyDto();
        dto.setName("P1");
        dto.setDescription("Desc");

        // Act: create
        String json = mockMvc.perform(post("/api/policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("P1")))
                .andReturn().getResponse().getContentAsString();
        PolicyDto created = objectMapper.readValue(json, PolicyDto.class);

        // Assert: get
        mockMvc.perform(get("/api/policies/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Desc")));

        // Act: update
        created.setDescription("NewDesc");
        mockMvc.perform(put("/api/policies/{id}", created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("NewDesc")));

        // Act & Assert: delete
        mockMvc.perform(delete("/api/policies/{id}", created.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/policies/{id}", created.getId()))
                .andExpect(status().isNotFound());
    }
}
