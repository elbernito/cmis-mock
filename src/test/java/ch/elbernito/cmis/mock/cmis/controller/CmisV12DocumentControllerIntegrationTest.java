package ch.elbernito.cmis.mock.cmis.controller;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.model.FolderModel;
import ch.elbernito.cmis.mock.model.ObjectModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.FolderRepository;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CmisV12DocumentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ObjectRepository cmisObjectRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FolderRepository folderRepository;

    private ObjectModel parentFolder;
    private DocumentModel documentModel;
    private FolderModel folderModel = new FolderModel();;

    @BeforeEach
    void setup() {
        cmisObjectRepository.deleteAll();
        documentRepository.deleteAll();
        folderRepository.deleteAll();


        // Lege erst den Folder an
        FolderModel folder = new FolderModel();
        folder.setFolderId("folder-1");
        folder.setName("Test-Folder");
        folder.setObjectId("folder-1");
        folder.setCreationDate(LocalDateTime.now());
        folder.setLastModifiedDate(LocalDateTime.now());
        folderRepository.save(folder);

        parentFolder = new ObjectModel();
        parentFolder.setObjectId("folder-1");
        parentFolder.setName("Test Folder");
        parentFolder.setPath("/folder");
        parentFolder.setType("Folder");
        parentFolder.setParentFolderId(null);
        cmisObjectRepository.save(parentFolder);

        documentModel = new DocumentModel();
        documentModel.setDocumentId("doc-1");
        documentModel.setObjectId("doc-1");
        documentModel.setName("Test Document");
        documentModel.setMimeType("text/plain");
        documentModel.setParentFolderId(parentFolder.getObjectId());
        documentModel.setTypeId("cmis:document");
        documentModel.setContent("Hallo Welt".getBytes());
        documentModel.setContentLength((long) documentModel.getContent().length);
        documentModel.setCreatedAt(LocalDateTime.now());
        documentModel.setLastModifiedAt(LocalDateTime.now());
        documentModel.setIsLatestVersion(true);
        documentModel.setVersionLabel("1.0");
        documentModel.setDescription("Eine Testdatei");
        documentModel.setCreatedBy("admin");
        documentModel.setLastModifiedBy("admin");

        documentRepository.save(documentModel);
    }

    @AfterEach
    void cleanup() {
        documentRepository.deleteAll();
        cmisObjectRepository.deleteAll();
        folderRepository.deleteAll();
    }

    @Test
    void testCreateDocument() throws Exception {
        DocumentDto newDoc = new DocumentDto();
        newDoc.setDocumentId("doc-2");
        newDoc.setObjectId("doc-2");
        newDoc.setName("Neues Dokument");
        newDoc.setMimeType("text/markdown");
        newDoc.setParentFolderId(parentFolder.getObjectId());
        newDoc.setTypeId("cmis:document");
        newDoc.setContent("Testcontent".getBytes());
        newDoc.setContentLength((long) newDoc.getContent().length);
        newDoc.setDescription("Beschreibung");

        mockMvc.perform(post("/api/cmis/v1.2/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDoc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documentId").value("doc-2"))
                .andExpect(jsonPath("$.name").value("Neues Dokument"))
                .andExpect(jsonPath("$.mimeType").value("text/markdown"))
                .andExpect(jsonPath("$.parentFolderId").value(parentFolder.getObjectId()))
                .andExpect(jsonPath("$.description").value("Beschreibung"));

        assertThat(documentRepository.findByDocumentId("doc-2")).isPresent();
    }

    @Test
    void testGetDocumentById() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/documents/{documentId}", documentModel.getDocumentId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documentId").value(documentModel.getDocumentId()))
                .andExpect(jsonPath("$.name").value(documentModel.getName()))
                .andExpect(jsonPath("$.mimeType").value(documentModel.getMimeType()));
    }

    @Test
    void testUpdateDocument() throws Exception {
        DocumentDto update = new DocumentDto();
        update.setDocumentId(documentModel.getDocumentId());
        update.setObjectId(documentModel.getObjectId());
        update.setName("Updated Name");
        update.setMimeType(documentModel.getMimeType());
        update.setParentFolderId(documentModel.getParentFolderId());
        update.setTypeId(documentModel.getTypeId());
        update.setContent(documentModel.getContent());
        update.setContentLength(documentModel.getContentLength());
        update.setCreatedBy(documentModel.getCreatedBy());
        update.setLastModifiedBy("admin");
        update.setDescription("Updated Description");

        mockMvc.perform(put("/api/cmis/v1.2/documents/{documentId}", documentModel.getDocumentId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.lastModifiedBy").value("admin"));

        assertThat(documentRepository.findByDocumentId(documentModel.getDocumentId())
                .get().getName()).isEqualTo("Updated Name");
    }

    @Test
    void testDeleteDocument() throws Exception {
        DocumentModel deleteDoc = new DocumentModel();
        deleteDoc.setDocumentId("doc-del");
        deleteDoc.setObjectId("doc-del");
        deleteDoc.setName("Delete Me");
        deleteDoc.setMimeType("text/plain");
        deleteDoc.setParentFolderId(parentFolder.getObjectId());
        deleteDoc.setTypeId("cmis:document");
        deleteDoc.setContent("Delete".getBytes());
        deleteDoc.setContentLength(6L);
        deleteDoc.setCreatedAt(LocalDateTime.now());
        deleteDoc.setLastModifiedAt(LocalDateTime.now());
        deleteDoc.setIsLatestVersion(true);
        deleteDoc.setVersionLabel("1.0");
        deleteDoc.setCreatedBy("tester");
        deleteDoc.setLastModifiedBy("tester");
        deleteDoc.setDescription("to delete");
        documentRepository.save(deleteDoc);

        mockMvc.perform(delete("/api/cmis/v1.2/documents/{documentId}", deleteDoc.getDocumentId()))
                .andExpect(status().isOk());

        assertThat(documentRepository.findByDocumentId("doc-del")).isNotPresent();
    }


    /**
     * Somethimes an error occours. This is the reason the test BeforeEach/AfterEach. Not good. but it helps
     * @throws Exception
     */
    @Test
    void testGetDocumentContent() throws Exception {

        mockMvc.perform(get("/api/cmis/v1.2/documents/{documentId}/content", documentModel.getDocumentId()))
                .andExpect(status().isOk())
                .andExpect(content().bytes(documentModel.getContent()));


    }

    @Test
    void testUpdateDocumentContent() throws Exception {
        byte[] newContent = "Updated Content".getBytes();

        mockMvc.perform(put("/api/cmis/v1.2/documents/{documentId}/content", documentModel.getDocumentId())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .content(newContent)
                        .param("mimeType", "text/plain"))
                .andExpect(status().isOk());

        DocumentModel updatedDoc = documentRepository.findByDocumentId(documentModel.getDocumentId()).orElseThrow();
        assertThat(updatedDoc.getContent()).isEqualTo(newContent);
    }

    @Test
    void testCheckinDocument() throws Exception {
        mockMvc.perform(post("/api/cmis/v1.2/documents/{documentId}/checkin", documentModel.getDocumentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documentId").value(documentModel.getDocumentId()));
    }

    @Test
    void testCheckoutDocument() throws Exception {
        mockMvc.perform(post("/api/cmis/v1.2/documents/{documentId}/checkout", documentModel.getDocumentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documentId").value(documentModel.getDocumentId()));
    }

    @Test
    void testGetDocumentVersions() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/documents/{documentId}/versions", documentModel.getDocumentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
