package ch.elbernito.cmis.mock.cmis.controller;

import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.model.ObjectModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.ObjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CmisV12ObjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectRepository cmisObjectRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String OBJECT_ID = "obj-1";
    private static final String OBJECT_PATH = "/folder/testobject";
    private static final String DOC_NAME = "Test Document";

    @BeforeEach
    void setUp() {
        // Clean up before each test
        documentRepository.deleteAll();
        cmisObjectRepository.deleteAll();

        // Create and save CMIS object
        ObjectModel cmisObject = new ObjectModel();
        cmisObject.setName(DOC_NAME);
        cmisObject.setObjectId(OBJECT_ID);
        cmisObject.setParentFolderId("folder-1");
        cmisObject.setPath(OBJECT_PATH);
        cmisObject.setType("cmis:document");
        cmisObjectRepository.save(cmisObject);

        // Create and save Document
        DocumentModel document = new DocumentModel();
        document.setDocumentId(OBJECT_ID);
        document.setObjectId(OBJECT_ID);
        document.setName(DOC_NAME);
        document.setMimeType("text/plain");
        document.setContent("Hello CMIS!".getBytes());
        document.setContentLength((long) document.getContent().length);
        document.setTypeId("cmis:document");
        document.setParentFolderId("folder-1");
        document.setIsLatestVersion(true);
        document.setVersionLabel("1.0");
        document.setCreatedAt(java.time.LocalDateTime.now());
        document.setLastModifiedAt(java.time.LocalDateTime.now());
        documentRepository.save(document);
    }

    @AfterEach
    void tearDown() {
        documentRepository.deleteAll();
        cmisObjectRepository.deleteAll();
    }

    @Test
    void testGetObjectById() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/objects/{objectId}", OBJECT_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.objectId").value(OBJECT_ID))
                .andExpect(jsonPath("$.name").value(DOC_NAME));
    }

    @Test
    void testGetObjectByPath() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/objects/by-path")
                        .param("path", OBJECT_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.objectId").value(OBJECT_ID))
                .andExpect(jsonPath("$.name").value(DOC_NAME));
    }

    @Test
    void testReturnNotFoundForUnknownObjectId() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/objects/{objectId}", "unknown-id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void testReturnNotFoundForUnknownPath() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/objects/by-path")
                        .param("path", "/does/not/exist")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }
}
