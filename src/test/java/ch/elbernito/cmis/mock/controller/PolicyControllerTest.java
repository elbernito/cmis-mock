package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.PolicyDto;
import ch.elbernito.cmis.mock.model.PolicyAssignmentModel;
import ch.elbernito.cmis.mock.repository.PolicyAssignmentRepository;
import ch.elbernito.cmis.mock.repository.PolicyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyAssignmentRepository policyAssignmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        policyRepository.deleteAll();
        policyAssignmentRepository.deleteAll();
    }

    @Test
    public void testCreateGetDeletePolicy() throws Exception {
        PolicyDto dto = PolicyDto.builder()
                .name("Retention Policy")
                .description("Retention test")
                .content("{\"retentionDays\":30}")
                .build();

        // Create
        String response = mockMvc.perform(post("/api/crud/policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Retention Policy")))
                .andReturn().getResponse().getContentAsString();

        PolicyDto created = objectMapper.readValue(response, PolicyDto.class);

        // Get
        mockMvc.perform(get("/api/crud/policies/" + created.getPolicyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Retention test")));

        // List all
        mockMvc.perform(get("/api/crud/policies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].policyId", is(created.getPolicyId())));

        // Delete
        mockMvc.perform(delete("/api/crud/policies/" + created.getPolicyId()))
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
        String response = mockMvc.perform(post("/api/crud/policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse().getContentAsString();

        PolicyDto created = objectMapper.readValue(response, PolicyDto.class);

        String objectId = "obj-1";
        String policyId = created.getPolicyId();

        // Apply
        mockMvc.perform(post("/api/crud/policies/apply")
                        .param("objectId", objectId)
                        .param("policyId", policyId))
                .andExpect(status().isNoContent());

        // Prüfe, ob die Policy-Zuweisung existiert
        assertTrue(policyAssignmentRepository.findByObjectIdAndPolicyId(objectId, policyId).isPresent(),
                "PolicyAssignment should exist after applying policy");

        // Remove
        mockMvc.perform(delete("/api/crud/policies/remove")
                        .param("objectId", objectId)
                        .param("policyId", policyId))
                .andExpect(status().isNoContent());

        // Prüfe, ob die Policy-Zuweisung entfernt wurde
        assertFalse(policyAssignmentRepository.findByObjectIdAndPolicyId(objectId, policyId).isPresent(),
                "PolicyAssignment should be removed after removing policy");
    }
}
