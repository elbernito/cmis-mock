package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.RetentionDto;
import ch.elbernito.cmis.mock.repository.RetentionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RetentionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RetentionRepository retentionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        retentionRepository.deleteAll();
    }

    @Test
    public void testCreateGetDeleteRetention() throws Exception {
        RetentionDto dto = RetentionDto.builder()
                .objectId("obj-1")
                .label("Archive")
                .retentionUntil(LocalDateTime.now().plusDays(30))
                .build();

        // Create
        String response = mockMvc.perform(post("/api/retentions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label", is("Archive")))
                .andReturn().getResponse().getContentAsString();

        RetentionDto created = objectMapper.readValue(response, RetentionDto.class);

        // Get
        mockMvc.perform(get("/api/retentions/" + created.getRetentionId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objectId", is("obj-1")));

        // List by object
        mockMvc.perform(get("/api/retentions/object/" + created.getObjectId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].label", is("Archive")));

        // Delete
        mockMvc.perform(delete("/api/retentions/" + created.getRetentionId()))
                .andExpect(status().isNoContent());
    }
}
