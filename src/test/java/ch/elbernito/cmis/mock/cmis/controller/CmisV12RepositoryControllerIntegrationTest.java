package ch.elbernito.cmis.mock.cmis.controller;

import ch.elbernito.cmis.mock.cmis.dto.RepositoryInfoDto;
import ch.elbernito.cmis.mock.cmis.dto.RepositoryMetaDto;
import ch.elbernito.cmis.mock.model.RepositoryModel;
import ch.elbernito.cmis.mock.repository.RepositoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CmisV12RepositoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepositoryRepository repositoryRepository; // dein Spring Data Interface

    @BeforeEach
    void setup() {
        repositoryRepository.deleteAll();
        RepositoryModel repo = new RepositoryModel();
        repo.setRepositoryId("default");
        repo.setName("Default Repository");
        repo.setDescription("Integration Test Repository");
        repo.setRootFolderId("root-folder-1");
        repo.setCapabilities("ALL");
        repositoryRepository.save(repo);
    }

    @AfterEach
    void tearDown() {
        repositoryRepository.deleteAll();
    }

    @Test
    void testGetAllRepositories() throws Exception {
        var mvcResult = mockMvc.perform(get("/api/cmis/v1.2/repositories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        List<RepositoryMetaDto> repositories =
                objectMapper.readValue(responseJson, new TypeReference<List<RepositoryMetaDto>>() {});

        assertThat(repositories).isNotNull();
        // Optional: Minimum number of repositories, adjust if you have test data
        // assertThat(repositories).isNotEmpty();
    }

    @Test
    void testGetRepositoryById() throws Exception {
        // Annahme: Es gibt mindestens eine Repository mit id "default" in den Testdaten
        String repoId = "default";
        var mvcResult = mockMvc.perform(get("/api/cmis/v1.2/repositories/{id}", repoId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        RepositoryMetaDto repository =
                objectMapper.readValue(responseJson, RepositoryMetaDto.class);

        assertThat(repository).isNotNull();
        assertThat(repository.getId()).isEqualTo(repoId);
    }

    @Test
    void testGetRepositoryInfo() throws Exception {
        String repoId = "default";
        var mvcResult = mockMvc.perform(get("/api/cmis/v1.2/repositories/{id}/info", repoId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        RepositoryInfoDto info =
                objectMapper.readValue(responseJson, RepositoryInfoDto.class);

        assertThat(info).isNotNull();
        assertThat(info.getRepositoryId()).isEqualTo(repoId);
    }

    @Test
    void testReturnNotFoundForUnknownRepository() throws Exception {
        String repoId = "does-not-exist";
        mockMvc.perform(get("/api/cmis/v1.2/repositories/{id}", repoId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Repository not found: " + repoId));
    }
}
