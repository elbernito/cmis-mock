package ch.elbernito.cmis.mock.cmis.controller;

import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.model.FolderModel;
import ch.elbernito.cmis.mock.repository.FolderRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CmisV12FolderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private FolderModel root;
    private FolderModel child;

    @BeforeEach
    void setup() {
        folderRepository.deleteAll();

        System.out.println("Write data");

        // Root folder anlegen
        root = new FolderModel();
        root.setFolderId("root");
        root.setName("Root Folder");
        root.setObjectId("root");
        root.setCreationDate(LocalDateTime.now());
        root.setLastModifiedDate(LocalDateTime.now());
        folderRepository.save(root);

        // Child folder anlegen
        child = new FolderModel();
        child.setFolderId("folder-1");
        child.setName("Child Folder");
        child.setObjectId("folder-1");
        child.setParentFolder(root);
        child.setCreationDate(LocalDateTime.now());
        child.setLastModifiedDate(LocalDateTime.now());
        folderRepository.save(child);
        System.out.println("Write data end");
    }

    @Test
    void testWhenFolderIdExistsThenReturnFolder() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/folders/folder-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.folderId").value("folder-1"))
                .andExpect(jsonPath("$.name").value("Child Folder"));
    }

    @Test
    void testWhenFolderIdDoesNotExistThenReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/folders/doesnotexist"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Folder not found: doesnotexist"));
    }

    @Test
    void testWhenCreatingFolderThenReturnCreatedFolder() throws Exception {
        FolderDto newFolder = new FolderDto();
        newFolder.setFolderId("folder-2");
        newFolder.setName("Neuer Unterordner");
        newFolder.setParentFolderId("root");
        newFolder.setObjectId("folder-2");

        mockMvc.perform(post("/api/cmis/v1.2/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newFolder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.folderId").value("folder-2"))
                .andExpect(jsonPath("$.name").value("Neuer Unterordner"));
    }

    @Test
    void testWhenUpdatingFolderThenReturnUpdatedFolder() throws Exception {
        FolderDto updateFolder = new FolderDto();
        updateFolder.setFolderId("folder-1");
        updateFolder.setName("Geänderter Name");
        updateFolder.setParentFolderId("root");
        updateFolder.setObjectId("folder-1");

        mockMvc.perform(put("/api/cmis/v1.2/folders/folder-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateFolder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Geänderter Name"));
    }

    @Test
    void testWhenDeletingFolderThenFolderIsRemoved() throws Exception {
        mockMvc.perform(delete("/api/cmis/v1.2/folders/folder-1"))
                .andExpect(status().isOk());

        assertThat(folderRepository.findByFolderId("folder-1")).isEmpty();
    }


    /**
     * Be aware. It Returns an object not a folder!
     * @throws Exception
     */
    @Test
    void testWhenGettingFolderChildrenThenReturnAllChildren() throws Exception {
        // Weiteres Kind anlegen
        FolderModel child2 = new FolderModel();
        child2.setFolderId("folder-3");
        child2.setName("Noch ein Kind");
        child2.setObjectId("folder-3");
        child2.setParentFolder(root);
        child2.setCreationDate(LocalDateTime.now());
        child2.setLastModifiedDate(LocalDateTime.now());
        folderRepository.save(child2);

        mockMvc.perform(get("/api/cmis/v1.2/folders/root/children"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.objectId == 'folder-1')]").exists())
                .andExpect(jsonPath("$[?(@.objectId == 'folder-3')]").exists());
    }

    @Test
    void testWhenGettingFolderParentThenReturnParentFolder() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/folders/folder-1/parent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.folderId").value("root"));
    }
}
