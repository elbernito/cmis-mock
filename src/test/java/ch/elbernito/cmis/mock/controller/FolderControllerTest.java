package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.repository.FolderRepository;
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
        String response = mockMvc.perform(post("/api/crud/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("RootFolder")))
                .andReturn().getResponse().getContentAsString();

        FolderDto created = objectMapper.readValue(response, FolderDto.class);

        // Get
        mockMvc.perform(get("/api/crud/folders/" + created.getFolderId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("RootFolder")));

        // Delete
        mockMvc.perform(delete("/api/crud/folders/" + created.getFolderId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testChildrenAndTree() throws Exception {
        // Create root
        FolderDto root = FolderDto.builder().name("Root").build();
        String rootResp = mockMvc.perform(post("/api/crud/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(root)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FolderDto rootDto = objectMapper.readValue(rootResp, FolderDto.class);

        // Create child
        FolderDto child = FolderDto.builder().name("Child").parentFolderId(rootDto.getFolderId()).build();
        String childResp = mockMvc.perform(post("/api/crud/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(child)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        FolderDto childDto = objectMapper.readValue(childResp, FolderDto.class);

        // Get children
        mockMvc.perform(get("/api/crud/folders/" + rootDto.getFolderId() + "/children"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // Get descendants
        mockMvc.perform(get("/api/crud/folders/" + rootDto.getFolderId() + "/descendants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // Get parent
        mockMvc.perform(get("/api/crud/folders/" + childDto.getFolderId() + "/parent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.folderId", is(rootDto.getFolderId())));

        // Delete tree
        mockMvc.perform(delete("/api/crud/folders/" + rootDto.getFolderId() + "/tree"))
                .andExpect(status().isNoContent());
    }
}
