package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.PolicyDto;
import ch.elbernito.cmis.mock.repository.PolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        policyRepository.deleteAll();
    }

    @Test
    public void testCreateGetDeletePolicy() throws Exception {
        PolicyDto dto = PolicyDto.builder()
                .name("Retention Policy")
                .description("Retention test")
                .content("{\"retentionDays\":30}")
                .build();

        // Create
        String response = mockMvc.perform(post("/api/policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Retention Policy")))
                .andReturn().getResponse().getContentAsString();

        PolicyDto created = objectMapper.readValue(response, PolicyDto.class);

        // Get
        mockMvc.perform(get("/api/policies/" + created.getPolicyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Retention test")));

        // List all
        mockMvc.perform(get("/api/policies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].policyId", is(created.getPolicyId())));

        // Delete
        mockMvc.perform(delete("/api/policies/" + created.getPolicyId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testApplyAndRemovePolicy() throws Exception {
        PolicyDto dto = PolicyDto.builder()
                .name("Security Policy")
                .description("Security rules")
                .content("{\"secure\":true}")
                .build();

        // Create
        String response = mockMvc.perform(post("/api/policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse().getContentAsString();

        PolicyDto created = objectMapper.readValue(response, PolicyDto.class);

        // Apply
        mockMvc.perform(post("/api/policies/apply")
                        .param("objectId", "obj-1")
                        .param("policyId", created.getPolicyId()))
                .andExpect(status().isNoContent());

        // Remove
        mockMvc.perform(delete("/api/policies/remove")
                        .param("objectId", "obj-1")
                        .param("policyId", created.getPolicyId()))
                .andExpect(status().isNoContent());
    }
}
