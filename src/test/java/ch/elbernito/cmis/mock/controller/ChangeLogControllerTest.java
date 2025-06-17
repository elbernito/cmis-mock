package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.ChangeLogDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ChangeLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetChangeLog() throws Exception {
        ChangeLogDto dto = new ChangeLogDto();
        dto.setObjectId("OBJ-1");
        dto.setChangeType("CREATED");
        dto.setChangedBy("system");
        dto.setChangeTime(LocalDateTime.now());

        String response = mockMvc.perform(post("/api/changelog")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objectId").value("OBJ-1"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ChangeLogDto created = objectMapper.readValue(response, ChangeLogDto.class);

        mockMvc.perform(get("/api/changelog/object/OBJ-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].objectId").value("OBJ-1"));
    }
}
