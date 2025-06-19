package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.model.ObjectModel;
import ch.elbernito.cmis.mock.repository.ObjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ObjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectRepository cmisObjectRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ObjectModel model;

    @BeforeEach
    public void setup() {
        cmisObjectRepository.deleteAll();
        model = ObjectModel.builder()
                .name("root")
                .type("folder")
                .parentFolderId(null)
                .path("/root")
                .build();
        model = cmisObjectRepository.save(model);
    }

    @Test
    public void testGetObjectById() throws Exception {
        mockMvc.perform(get("/api/crud/objects/" + model.getObjectId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("root")));
    }

    @Test
    public void testGetObjectByPath() throws Exception {
        mockMvc.perform(get("/api/crud/objects/by-path").param("path", "/root"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("folder")));
    }

    @Test
    public void testMoveObject() throws Exception {
        // Create a new folder
        ObjectModel targetFolder = ObjectModel.builder()
                .name("target")
                .type("folder")
                .path("/target")
                .build();
        targetFolder = cmisObjectRepository.save(targetFolder);

        mockMvc.perform(post("/api/crud/objects/" + model.getObjectId() + "/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"targetFolderId\": \"" + targetFolder.getObjectId() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parentFolderId", is(targetFolder.getObjectId())));
    }

    @Test
    public void testCopyObject() throws Exception {
        // Create a new folder
        ObjectModel targetFolder = ObjectModel.builder()
                .name("target")
                .type("folder")
                .path("/target")
                .build();
        targetFolder = cmisObjectRepository.save(targetFolder);

        mockMvc.perform(post("/api/crud/objects/" + model.getObjectId() + "/copy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"targetFolderId\": \"" + targetFolder.getObjectId() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parentFolderId", is(targetFolder.getObjectId())));
    }

    @Test
    public void testGetAllowableActions() throws Exception {
        mockMvc.perform(get("/api/crud/objects/" + model.getObjectId() + "/allowableActions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("read")));
    }
}
