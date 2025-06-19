package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.MetadataDto;
import ch.elbernito.cmis.mock.repository.MetadataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MetadataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MetadataRepository metadataRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        metadataRepository.deleteAll();
    }

    @Test
    public void testCreateGetUpdateDeleteMetadata() throws Exception {
        MetadataDto dto = MetadataDto.builder()
                .documentId("doc1")
                .key("author")
                .value("Bernhard")
                .build();

        // Create
        String response = mockMvc.perform(post("/api/metadata")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key", is("author")))
                .andReturn().getResponse().getContentAsString();

        MetadataDto created = objectMapper.readValue(response, MetadataDto.class);

        // Get
        mockMvc.perform(get("/api/metadata/" + created.getMetadataId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", is("Bernhard")));

        // Update
        created.setValue("B. Brunner");
        mockMvc.perform(put("/api/metadata/" + created.getMetadataId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", is("B. Brunner")));

        // Get by DocumentId
        mockMvc.perform(get("/api/metadata/document/" + created.getDocumentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].key", is("author")));

        // Delete
        mockMvc.perform(delete("/api/metadata/" + created.getMetadataId()))
                .andExpect(status().isNoContent());
    }
}
