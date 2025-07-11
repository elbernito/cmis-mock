package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.AclDto;
import ch.elbernito.cmis.mock.repository.AclRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AclControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AclRepository aclRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        aclRepository.deleteAll();
    }

    @Test
    public void testSetGetUpdateDeleteAcl() throws Exception {
        String objectId = "obj-4711";
        AclDto dto = AclDto.builder()
                .principal("userA")
                .permissions(List.of("cmis:read", "cmis:write"))
                .build();

        // Set ACL
        String response = mockMvc.perform(put("/api/crud/objects/" + objectId + "/acl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.principal", is("userA")))
                .andExpect(jsonPath("$.permissions", containsInAnyOrder("cmis:read", "cmis:write")))
                .andReturn().getResponse().getContentAsString();

        AclDto created = objectMapper.readValue(response, AclDto.class);

        // Get ACL
        mockMvc.perform(get("/api/crud/objects/" + objectId + "/acl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].permissions", containsInAnyOrder("cmis:read", "cmis:write")));

        // Update ACL
        created.setPermissions(List.of("cmis:read", "cmis:write", "cmis:all"));
        mockMvc.perform(put("/api/crud/objects/" + objectId + "/acl/" + created.getAclId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.permissions", containsInAnyOrder("cmis:read", "cmis:write", "cmis:all")));

        // Delete ACL
        mockMvc.perform(delete("/api/crud/objects/" + objectId + "/acl/" + created.getAclId()))
                .andExpect(status().isNoContent());
    }
}
