package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.RetentionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RetentionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RetentionDto testDto;

    @BeforeEach
    void setup() {
        // RetentionId nicht setzen, wird durch die DB vergeben!
        testDto = new RetentionDto();
        testDto.setObjectId("doc-1");
        testDto.setRetentionStart(LocalDateTime.now().minusDays(1));
        testDto.setRetentionEnd(LocalDateTime.now().plusDays(30));
        testDto.setDescription("Sample retention");
        testDto.setActive(true);
    }

    @Test
    void createAndGetRetention() throws Exception {
        // Create
        String response = mockMvc.perform(post("/api/retentions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objectId").value("doc-1"))
                .andReturn().getResponse().getContentAsString();

        // ID aus Response holen
        String id = objectMapper.readTree(response).get("retentionId").asText();

        // Get retention
        mockMvc.perform(get("/api/retentions/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objectId").value("doc-1"))
                .andExpect(jsonPath("$.description").value("Sample retention"));
    }

    @Test
    void updateRetention() throws Exception {
        // Erstellt retention
        String response = mockMvc.perform(post("/api/retentions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(response).get("retentionId").asText();

        // Update DTO
        testDto.setDescription("Changed!");
        testDto.setActive(false);

        mockMvc.perform(put("/api/retentions/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Changed!"))
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    void deleteRetention() throws Exception {
        // Erstellt retention
        String response = mockMvc.perform(post("/api/retentions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(response).get("retentionId").asText();

        // Löschen
        mockMvc.perform(delete("/api/retentions/" + id))
                .andExpect(status().isOk());

        // Muss 404 zurückgeben (not found)
        mockMvc.perform(get("/api/retentions/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void listRetentions() throws Exception {
        // Erstellt retention
        mockMvc.perform(post("/api/retentions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDto)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/retentions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].objectId").value("doc-1"));
    }
}
