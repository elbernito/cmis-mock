package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.repository.FolderRepository;
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
public class FolderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        folderRepository.deleteAll();
    }

    @Test
    public void testCreateGetDeleteFolder() throws Exception {
        FolderDto dto = FolderDto.builder().name("RootFolder").build();

        // Create
        String response = mockMvc.perform(post("/api/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("RootFolder")))
                .andReturn().getResponse().getContentAsString();

        FolderDto created = objectMapper.readValue(response, FolderDto.class);

        // Get
        mockMvc.perform(get("/api/folders/" + created.getFolderId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("RootFolder")));

        // Delete
        mockMvc.perform(delete("/api/folders/" + created.getFolderId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testChildrenAndTree() throws Exception {
        // Create root
        FolderDto root = FolderDto.builder().name("Root").build();
        String rootResp = mockMvc.perform(post("/api/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(root)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FolderDto rootDto = objectMapper.readValue(rootResp, FolderDto.class);

        // Create child
        FolderDto child = FolderDto.builder().name("Child").parentFolderId(rootDto.getFolderId()).build();
        String childResp = mockMvc.perform(post("/api/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(child)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FolderDto childDto = objectMapper.readValue(childResp, FolderDto.class);

        // Get children
        mockMvc.perform(get("/api/folders/" + rootDto.getFolderId() + "/children"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // Get descendants
        mockMvc.perform(get("/api/folders/" + rootDto.getFolderId() + "/descendants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // Get parent
        mockMvc.perform(get("/api/folders/" + childDto.getFolderId() + "/parent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.folderId", is(rootDto.getFolderId())));

        // Delete tree
        mockMvc.perform(delete("/api/folders/" + rootDto.getFolderId() + "/tree"))
                .andExpect(status().isNoContent());
    }
}
