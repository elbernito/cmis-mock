package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.AclDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for AclController
 */
@SpringBootTest
@AutoConfigureMockMvc
class AclControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateListGetDeleteAcl() throws Exception {
        // Arrange
        AclDto dto = new AclDto();
        dto.setObjectId("obj-1");
        dto.setPrincipal("user1");
        dto.setPermission("read");
        dto.setGranted(true);

        // Act: create
        String json = mockMvc.perform(post("/api/acls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.principal", is("user1")))
                .andReturn().getResponse().getContentAsString();
        AclDto created = objectMapper.readValue(json, AclDto.class);

        // Assert: list by object
        mockMvc.perform(get("/api/acls").param("objectId", "obj-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", hasItem(created.getId())));

        // Assert: get
        mockMvc.perform(get("/api/acls/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.permission", is("read")));

        // Act & Assert: delete
        mockMvc.perform(delete("/api/acls/{id}", created.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/acls/{id}", created.getId()))
                .andExpect(status().isNotFound());
    }
}
