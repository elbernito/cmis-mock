package ch.elbernito.cmis.mock.cmis.controller;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.dto.VersionDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        // 2. Dokument anlegen (Version 1.0)
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

        // 3. CheckIn: Neue Version (optional, je nach CMIS-API, so könnte sie aussehen)
        mockMvc.perform(post("/api/cmis/v1.2/documents/" + document.getDocumentId() + "/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "testUser")
                .content("U0dGc2JHOD0=") // neue Version-Inhalt als Base64
        ).andExpect(status().isOk());
    }

    @Test
    void testWhenGettingAllVersionsThenReturnsList() throws Exception {
        String response = mockMvc.perform(get("/api/cmis/v1.2/documents/" + document.getDocumentId() + "/versions"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<VersionDto> versions = objectMapper.readValue(response, new TypeReference<List<VersionDto>>() {
        });

        // Prüfen, dass mindestens ein Eintrag vorhanden ist
        assertThat(versions).isNotNull();
        assertThat(versions.size()).isGreaterThanOrEqualTo(1);



        // Beispiel: Prüfen, dass die erste Version ein bestimmtes Feld hat
        // assertThat(versions.get(0).getDocumentId()).isEqualTo(document.getDocumentId());
    }

    @Test
    void testWhenGettingVersionByIdThenReturnsVersion() throws Exception {
        // Liste aller Versionen abfragen
        String resp = mockMvc.perform(get("/api/cmis/v1.2/documents/" + document.getDocumentId() + "/versions"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        VersionDto[] versions = objectMapper.readValue(resp, VersionDto[].class);

        // Nimm die erste Version und lese sie explizit ab
        if (versions.length > 0) {
            mockMvc.perform(get("/api/cmis/v1.2/versions/" + versions[0].getVersionId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.versionId", is(versions[0].getVersionId())));
        }
    }

}
