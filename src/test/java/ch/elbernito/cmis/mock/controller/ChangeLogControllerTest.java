package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.ChangeLogDto;
import ch.elbernito.cmis.mock.repository.ChangeLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChangeLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChangeLogRepository changeLogRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        changeLogRepository.deleteAll();
    }

    @Test
    public void testAddAndGetChangeLogEntry() throws Exception {
        ChangeLogDto dto = ChangeLogDto.builder()
                .objectId("obj-1")
                .changeType("CREATE")
                .summary("Test creation")
                .changeTime(LocalDateTime.now())
                .build();

        // Add
        String response = mockMvc.perform(post("/api/crud/changelog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.changeType", is("CREATE")))
                .andReturn().getResponse().getContentAsString();

        ChangeLogDto created = objectMapper.readValue(response, ChangeLogDto.class);

        // Get all
        mockMvc.perform(get("/api/crud/changelog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].changelogId", is(created.getChangelogId())));

        // Get by object
        mockMvc.perform(get("/api/crud/changelog/object/" + created.getObjectId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].summary", is("Test creation")));
    }
}
