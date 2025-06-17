package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.VersionDto;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.VersionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VersionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private DocumentModel testDocument;

    @BeforeEach
    void setUp() {
        versionRepository.deleteAll();
        documentRepository.deleteAll();

        testDocument = new DocumentModel();
        testDocument.setName("TestDoc");
        testDocument.setCreatedBy("testuser");
        testDocument.setCreationDate(LocalDateTime.now());
        testDocument.setLastModifiedBy("testuser");
        testDocument.setLastModifiedDate(LocalDateTime.now());
        testDocument.setObjectId(UUID.randomUUID().toString());
        testDocument.setTypeId(UUID.randomUUID().toString());
        documentRepository.save(testDocument);
    }

    @Test
    @Order(1)
    void testCreateVersion() throws Exception {
        VersionDto dto = new VersionDto();
        dto.setDocumentId(testDocument.getId());
        dto.setVersionLabel("1.0");
        dto.setMajorVersion(true);
        dto.setCreationDate(LocalDateTime.now());
        dto.setCreatedBy("testuser");
        dto.setComment("Initial Version");

        mockMvc.perform(post("/api/versions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.versionLabel").value("1.0"));
    }

    @Test
    @Order(2)
    void testGetVersion() throws Exception {
        VersionDto version = new VersionDto();
        version.setDocumentId(testDocument.getId());
        version.setVersionLabel("1.1");
        version.setMajorVersion(false);
        version.setCreationDate(LocalDateTime.now());
        version.setCreatedBy("tester");
        version.setComment("Minor Update");

        VersionDto saved = objectMapper.readValue(
                mockMvc.perform(post("/api/versions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(version)))
                        .andReturn().getResponse().getContentAsString(),
                VersionDto.class);

        mockMvc.perform(get("/api/versions/{id}", saved.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.versionLabel").value("1.1"));
    }

    @Test
    @Order(3)
    void testGetVersionsByDocument() throws Exception {
        VersionDto version = new VersionDto();
        version.setDocumentId(testDocument.getId());
        version.setVersionLabel("1.2");
        version.setMajorVersion(true);
        version.setCreationDate(LocalDateTime.now());
        version.setCreatedBy("testuser2");
        version.setComment("Major Update");
        mockMvc.perform(post("/api/versions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(version)))
            .andExpect(status().isOk());

        mockMvc.perform(get("/api/versions/document/" + testDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].versionLabel").exists());
    }

    @Test
    @Order(4)
    void testUpdateVersion() throws Exception {
        VersionDto version = new VersionDto();
        version.setDocumentId(testDocument.getId());
        version.setVersionLabel("1.3");
        version.setMajorVersion(false);
        version.setCreationDate(LocalDateTime.now());
        version.setCreatedBy("testuser3");
        version.setComment("Before Update");

        VersionDto saved = objectMapper.readValue(
                mockMvc.perform(post("/api/versions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(version)))
                        .andReturn().getResponse().getContentAsString(),
                VersionDto.class);

        saved.setVersionLabel("1.3.1");
        saved.setComment("Updated!");

        mockMvc.perform(put("/api/versions/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saved)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.versionLabel").value("1.3.1"))
            .andExpect(jsonPath("$.comment").value("Updated!"));
    }

    @Test
    @Order(5)
    void testDeleteVersion() throws Exception {
        VersionDto version = new VersionDto();
        version.setDocumentId(testDocument.getId());
        version.setVersionLabel("2.0");
        version.setMajorVersion(true);
        version.setCreationDate(LocalDateTime.now());
        version.setCreatedBy("testuser4");
        version.setComment("Delete me");

        VersionDto saved = objectMapper.readValue(
                mockMvc.perform(post("/api/versions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(version)))
                        .andReturn().getResponse().getContentAsString(),
                VersionDto.class);

        mockMvc.perform(delete("/api/versions/" + saved.getId()))
            .andExpect(status().isOk());

        assertThat(versionRepository.findById(saved.getId())).isEmpty();
    }
}
