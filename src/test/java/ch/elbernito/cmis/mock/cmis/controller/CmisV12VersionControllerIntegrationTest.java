package ch.elbernito.cmis.mock.cmis.controller;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.dto.VersionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CmisV12VersionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private FolderDto folder;
    private DocumentDto document;

    @BeforeEach
    void setUp() throws Exception {
        // 1. Ordner anlegen
        folder = FolderDto.builder().name("TestFolder").build();
        String folderResp = mockMvc.perform(post("/api/cmis/v1.2/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(folder)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        folder = objectMapper.readValue(folderResp, FolderDto.class);

        // 2. Dokument anlegen
        document = DocumentDto.builder()
                .name("TestDoc")
                .mimeType("text/plain")
                .parentFolderId(folder.getFolderId())
                .content("SGFsbG8=".getBytes()) // "Hallo" Base64 als Byte-Array
                .build();
        String docResp = mockMvc.perform(post("/api/cmis/v1.2/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(document)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        document = objectMapper.readValue(docResp, DocumentDto.class);
    }

    @Test
    void testWhenCreatingVersionThenSuccess() throws Exception {
        VersionDto version = VersionDto.builder()
                .objectId(document.getDocumentId())
                .versionLabel("v1.1")
                .build();
        mockMvc.perform(post("/api/cmis/v1.2/versions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(version)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documentId", is(document.getDocumentId())))
                .andExpect(jsonPath("$.versionLabel", is("v1.1")));
    }

    @Test
    void testWhenGettingAllVersionsThenReturnsList() throws Exception {
        // Erstellt noch eine weitere Version
        VersionDto version = VersionDto.builder()
                .objectId(document.getDocumentId())
                .versionLabel("v2.0")
                .build();
        mockMvc.perform(post("/api/cmis/v1.2/versions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(version)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/cmis/v1.2/documents/" + document.getDocumentId() + "/versions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void testWhenGettingVersionByIdThenReturnsVersion() throws Exception {
        VersionDto version = VersionDto.builder()
                .objectId(document.getDocumentId())
                .versionLabel("v1.2")
                .build();
        String versionResp = mockMvc.perform(post("/api/cmis/v1.2/versions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(version)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        VersionDto savedVersion = objectMapper.readValue(versionResp, VersionDto.class);

        mockMvc.perform(get("/api/cmis/v1.2/versions/" + savedVersion.getVersionId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedVersion.getVersionId())));
    }

    @Test
    void testWhenDeletingVersionThenNoContent() throws Exception {
        VersionDto version = VersionDto.builder()
                .objectId(document.getDocumentId())
                .versionLabel("vX")
                .build();
        String versionResp = mockMvc.perform(post("/api/cmis/v1.2/versions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(version)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        VersionDto savedVersion = objectMapper.readValue(versionResp, VersionDto.class);

        mockMvc.perform(delete("/api/cmis/v1.2/versions/" + savedVersion.getVersionId()))
                .andExpect(status().isNoContent());
    }
}
