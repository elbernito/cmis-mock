package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.FolderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for FolderController
 */
@SpringBootTest
@AutoConfigureMockMvc
class FolderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateGetUpdateDeleteFolder() throws Exception {
        // Arrange
        FolderDto createDto = new FolderDto();
        createDto.setName("New Folder");

        // Act: create
        String json = mockMvc.perform(post("/api/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("New Folder")))
                .andReturn().getResponse().getContentAsString();
        FolderDto dto = objectMapper.readValue(json, FolderDto.class);

        // Assert: get
        mockMvc.perform(get("/api/folders/{id}", dto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId())));

        // Act: update
        dto.setName("Updated Folder");
        mockMvc.perform(put("/api/folders/{id}", dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Folder")));

        // Act & Assert: delete
        mockMvc.perform(delete("/api/folders/{id}", dto.getId()))
                .andExpect(status().isNoContent());

        // Assert: not found
        mockMvc.perform(get("/api/folders/{id}", dto.getId()))
                .andExpect(status().isNotFound());
    }
}
