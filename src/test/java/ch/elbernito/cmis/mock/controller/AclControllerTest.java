package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.AclDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AclControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetAcl() throws Exception {
        AclDto acl = new AclDto();
        acl.setPrincipal("user");
        acl.setPermission("read");

        String response = mockMvc.perform(post("/api/acl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(acl)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.principal").value("user"))
                .andExpect(jsonPath("$.permission").value("read"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        AclDto created = objectMapper.readValue(response, AclDto.class);

        mockMvc.perform(get("/api/acl/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.principal").value("user"))
                .andExpect(jsonPath("$.permission").value("read"));
    }
}
