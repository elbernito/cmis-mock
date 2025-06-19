package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.RepositoryDto;
import ch.elbernito.cmis.mock.repository.RepositoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepositoryRepository repositoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        repositoryRepository.deleteAll();
    }

    @Test
    public void testCreateAndGetRepository() throws Exception {
        RepositoryDto dto = RepositoryDto.builder()
                .name("Test Repo")
                .description("Desc")
                .capabilities("{\"capabilityA\":true}")
                .build();

        // Create
        String response = mockMvc.perform(post("/api/crud/repositories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Repo")))
                .andReturn().getResponse().getContentAsString();

        RepositoryDto created = objectMapper.readValue(response, RepositoryDto.class);

        // Get by ID
        mockMvc.perform(get("/api/crud/repositories/" + created.getRepositoryId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Repo")));

        // Get all
        mockMvc.perform(get("/api/crud/repositories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // Delete
        mockMvc.perform(delete("/api/crud/repositories/" + created.getRepositoryId()))
                .andExpect(status().isNoContent());
    }
}
