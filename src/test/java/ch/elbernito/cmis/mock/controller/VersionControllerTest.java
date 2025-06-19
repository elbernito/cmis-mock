package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.model.VersionModel;
import ch.elbernito.cmis.mock.repository.VersionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VersionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String objectId;

    @BeforeEach
    public void setup() {
        versionRepository.deleteAll();
        objectId = UUID.randomUUID().toString();
        VersionModel v1 = VersionModel.builder()
                .objectId(objectId)
                .versionLabel("1.0")
                .isLatestVersion(false)
                .build();
        VersionModel v2 = VersionModel.builder()
                .objectId(objectId)
                .versionLabel("2.0")
                .isLatestVersion(true)
                .build();
        versionRepository.save(v1);
        versionRepository.save(v2);
    }

    @Test
    public void testGetVersionById() throws Exception {
        VersionModel any = versionRepository.findAll().get(0);
        mockMvc.perform(get("/api/crud/versions/" + any.getVersionId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.versionLabel", is(any.getVersionLabel())));
    }

    @Test
    public void testGetVersionsForDocument() throws Exception {
        mockMvc.perform(get("/api/crud/versions/document/" + objectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetLatestVersion() throws Exception {
        mockMvc.perform(get("/api/crud/versions/document/" + objectId + "/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isLatestVersion", is(true)));
    }
}
