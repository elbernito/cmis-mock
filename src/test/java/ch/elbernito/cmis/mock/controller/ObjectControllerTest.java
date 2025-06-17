package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.ObjectDto;
import ch.elbernito.cmis.mock.repository.ObjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for ObjectController.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ObjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectRepository.deleteAll();
    }

    @Test
    void testCreateAndGetObject() throws Exception {
        ObjectDto dto = new ObjectDto();
        dto.setObjectId(1L);
        dto.setName("Test Object");
        dto.setCreatedBy("testuser");
        dto.setCreationDate(LocalDateTime.now());
        dto.setTypeId("cmis:customObject");

        String response = mockMvc.perform(post("/api/objects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectDto created = objectMapper.readValue(response, ObjectDto.class);
        assertThat(created.getId()).isNotNull();

        // Get by id
        mockMvc.perform(get("/api/objects/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Object"));
    }

    @Test
    void testUpdateObject() throws Exception {
        ObjectDto dto = new ObjectDto();
        dto.setObjectId(2L);
        dto.setName("Update Me");
        dto.setCreatedBy("updater");
        dto.setCreationDate(LocalDateTime.now());
        dto.setTypeId("cmis:customObject");

        String response = mockMvc.perform(post("/api/objects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectDto created = objectMapper.readValue(response, ObjectDto.class);

        // Update
        created.setName("Updated Object");
        created.setLastModifiedBy("modifier");
        created.setLastModificationDate(LocalDateTime.now());

        mockMvc.perform(put("/api/objects/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Object"));
    }

    @Test
    void testDeleteObject() throws Exception {
        ObjectDto dto = new ObjectDto();
        dto.setObjectId(2L);
        dto.setName("Delete Me");
        dto.setCreatedBy("deleter");
        dto.setCreationDate(LocalDateTime.now());
        dto.setTypeId("cmis:customObject");

        String response = mockMvc.perform(post("/api/objects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectDto created = objectMapper.readValue(response, ObjectDto.class);

        mockMvc.perform(delete("/api/objects/" + created.getId()))
                .andExpect(status().isNoContent());

        assertThat(objectRepository.findById(created.getId())).isEmpty();
    }
}
