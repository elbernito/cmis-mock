package ch.elbernito.cmis.mock.cmis.controller;

import ch.elbernito.cmis.mock.dto.RetentionDto;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.model.RetentionModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.RetentionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CmisV12RetentionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RetentionRepository retentionRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private DocumentModel testObj;
    private RetentionModel ret1;
    private RetentionModel ret2;

    @BeforeEach
    void setup() {
        retentionRepository.deleteAll();
        documentRepository.deleteAll();

        // Referenzobjekt anlegen (z.B. Dokument)
        testObj = new DocumentModel();
        testObj.setDocumentId("object-1");
        testObj.setName("Test Object");
        testObj.setMimeType("text/plain");
        testObj.setCreatedAt(LocalDateTime.now());
        documentRepository.save(testObj);

        // Retention-Einträge anlegen
        ret1 = RetentionModel.builder()
                .retentionId(UUID.randomUUID().toString())
                .objectId("object-1")
                .label("7 Years")
                .retentionUntil(LocalDateTime.now().plusYears(7))
                .creationDate(LocalDateTime.now())
                .build();
        retentionRepository.save(ret1);

        ret2 = RetentionModel.builder()
                .retentionId(UUID.randomUUID().toString())
                .objectId("object-1")
                .label("2 Years")
                .retentionUntil(LocalDateTime.now().plusYears(2))
                .creationDate(LocalDateTime.now())
                .build();
        retentionRepository.save(ret2);
    }

    @Test
    void testWhenRetentionIdExistsThenReturnRetention() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/retentions/{retentionId}", ret1.getRetentionId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.retentionId").value(ret1.getRetentionId()))
                .andExpect(jsonPath("$.label").value("7 Years"))
                .andExpect(jsonPath("$.objectId").value("object-1"));
    }

    @Test
    void testWhenRetentionIdDoesNotExistThenReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/retentions/{retentionId}", "does-not-exist-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testWhenCreatingRetentionThenReturnCreatedRetention() throws Exception {
        RetentionDto newRet = new RetentionDto();
        newRet.setObjectId("object-1");
        newRet.setLabel("10 Years");
        newRet.setRetentionUntil(LocalDateTime.now().plusYears(10));

        mockMvc.perform(post("/api/cmis/v1.2/retentions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objectId").value("object-1"))
                .andExpect(jsonPath("$.label").value("10 Years"))
                .andExpect(jsonPath("$.retentionId").isNotEmpty());
    }

    @Test
    void testWhenDeletingRetentionThenRetentionIsRemoved() throws Exception {
        mockMvc.perform(delete("/api/cmis/v1.2/retentions/{retentionId}", ret2.getRetentionId()))
                .andExpect(status().isOk());

        Optional<RetentionModel> deleted = retentionRepository.findByRetentionId(ret2.getRetentionId());
        assertThat(deleted).isEmpty();
    }

    @Test
    void testWhenGettingAllRetentionsForObjectThenReturnAll() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/objects/{objectId}/retentions", "object-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.retentionId == '%s')]", ret1.getRetentionId()).exists())
                .andExpect(jsonPath("$[?(@.retentionId == '%s')]", ret2.getRetentionId()).exists());
    }
}
