package ch.elbernito.cmis.mock.cmis.controller;

import ch.elbernito.cmis.mock.model.ChangeLogModel;
import ch.elbernito.cmis.mock.repository.ChangeLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CmisV12ChangeLogControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChangeLogRepository changeLogRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ChangeLogModel entry1;
    private ChangeLogModel entry2;

    @BeforeEach
    void setup() {
        changeLogRepository.deleteAll();

        entry1 = ChangeLogModel.builder()
                .changelogId(UUID.randomUUID().toString())
                .objectId("doc-1")
                .changeType("CREATE")
                .summary("Dokument erstellt")
                .changeTime(LocalDateTime.now().minusDays(2))
                .build();
        changeLogRepository.save(entry1);

        entry2 = ChangeLogModel.builder()
                .changelogId(UUID.randomUUID().toString())
                .objectId("doc-2")
                .changeType("DELETE")
                .summary("Dokument gelöscht")
                .changeTime(LocalDateTime.now().minusDays(1))
                .build();
        changeLogRepository.save(entry2);
    }

    @Test
    void testWhenGettingChangeLogThenReturnAllEntries() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/changelog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.changelogId == '%s')]", entry1.getChangelogId()).exists())
                .andExpect(jsonPath("$[?(@.changelogId == '%s')]", entry2.getChangelogId()).exists());
    }

    @Test
    void testWhenGettingContentChangesThenReturnAllEntries() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/changelog/changes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.changelogId == '%s')]", entry1.getChangelogId()).exists())
                .andExpect(jsonPath("$[?(@.changelogId == '%s')]", entry2.getChangelogId()).exists());
    }
}
