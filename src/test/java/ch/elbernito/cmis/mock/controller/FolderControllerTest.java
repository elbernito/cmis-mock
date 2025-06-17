package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.FolderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for FolderController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class FolderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetFolder() throws Exception {
        FolderDto folder = FolderDto.builder()
                .objectId("fld-test-001")
                .name("Test Folder")
                .typeId("cmis:folder")
                .creationDate(LocalDateTime.now())
                .createdBy("tester")
                .lastModifiedDate(LocalDateTime.now())
                .lastModifiedBy("tester")
                .build();

        // Create
        mockMvc.perform(post("/api/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(folder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objectId").value("fld-test-001"))
                .andExpect(jsonPath("$.name").value("Test Folder"));

        // Get by objectId
        mockMvc.perform(get("/api/folders/object/fld-test-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Folder"));
    }

    @Test
    void testGetAllFolders() throws Exception {
        mockMvc.perform(get("/api/folders"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteFolderNotFound() throws Exception {
        mockMvc.perform(delete("/api/folders/99999"))
                .andExpect(status().isNotFound());
    }
}
