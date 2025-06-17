package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.StatisticsDto;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.model.FolderModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.FolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FolderRepository folderRepository;

    @BeforeEach
    void setup() {
        documentRepository.deleteAll();
        folderRepository.deleteAll();

        DocumentModel doc1 = new DocumentModel();
        doc1.setObjectId(UUID.randomUUID().toString());
        doc1.setName("doc1");
        doc1.setContentSize(100L);
        doc1.setCreatedBy("user");
        doc1.setLastModifiedBy("user");
        doc1.setCreationDate(LocalDateTime.now());
        doc1.setLastModifiedBy("user");
        doc1.setLastModifiedDate(LocalDateTime.now());
        doc1.setTypeId("type1");
        doc1.setCreationDate(LocalDateTime.now());

        DocumentModel doc2 = new DocumentModel();
        doc2.setObjectId(UUID.randomUUID().toString());
        doc2.setName("doc2");
        doc2.setContentSize(250L);
        doc2.setCreatedBy("user");
        doc2.setLastModifiedBy("user");
        doc2.setCreationDate(LocalDateTime.now());
        doc2.setLastModifiedBy("user");
        doc2.setLastModifiedDate(LocalDateTime.now());
        doc2.setTypeId("type2");
        doc2.setCreationDate(LocalDateTime.now());

        FolderModel folder1 = new FolderModel();
        folder1.setObjectId(UUID.randomUUID().toString());
        folder1.setName("folder1");
        folder1.setCreatedBy("user");
        folder1.setLastModifiedBy("user");
        folder1.setLastModifiedDate(LocalDateTime.now());
        folder1.setTypeId("type1");
        folder1.setCreationDate(LocalDateTime.now());
        folder1.setLastModifiedBy("user");
        folder1.setLastModifiedDate(LocalDateTime.now());
        folder1.setCreatedBy("user");

        documentRepository.save(doc1);
        documentRepository.save(doc2);
        folderRepository.save(folder1);
    }

    @Test
    void testGetCurrentStatistics() throws Exception {
        mockMvc.perform(get("/api/statistics/current").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.documentCount").value(2))
            .andExpect(jsonPath("$.folderCount").value(1))
            .andExpect(jsonPath("$.objectCount").value(3))
            .andExpect(jsonPath("$.storageSize").value(350));
    }
}
