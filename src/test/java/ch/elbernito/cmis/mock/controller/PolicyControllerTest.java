package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.PolicyDto;
import ch.elbernito.cmis.mock.repository.PolicyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private PolicyDto testPolicy;

    @BeforeAll
    void cleanUp() {
        policyRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        policyRepository.deleteAll();

        testPolicy = new PolicyDto();
        testPolicy.setPolicyId("test-policy-1");
        testPolicy.setName("Test Policy");
        testPolicy.setPolicyText("Text of Policy");
        testPolicy.setCreatedBy("admin");
        testPolicy.setObjectId("object-123");
    }

    @Test
    void testCreateAndGetPolicy() throws Exception {
        // Create
        String response = mockMvc.perform(post("/api/policy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPolicy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.policyId").value("test-policy-1"))
                .andReturn().getResponse().getContentAsString();

        PolicyDto created = objectMapper.readValue(response, PolicyDto.class);
        assertThat(created.getId()).isNotNull();

        // Get by id
        mockMvc.perform(get("/api/policy/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.policyId").value("test-policy-1"));

        // Get by policyId
        mockMvc.perform(get("/api/policy/byPolicyId/test-policy-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Policy"));

        // Get all
        mockMvc.perform(get("/api/policy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].policyId").value("test-policy-1"));
    }

    @Test
    void testUpdatePolicy() throws Exception {
        // Erst anlegen
        String response = mockMvc.perform(post("/api/policy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPolicy)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PolicyDto created = objectMapper.readValue(response, PolicyDto.class);

        // Update
        created.setName("Updated Policy");
        created.setPolicyText("Updated Text");
        mockMvc.perform(put("/api/policy/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Policy"))
                .andExpect(jsonPath("$.policyText").value("Updated Text"));
    }

    @Test
    void testDeletePolicy() throws Exception {
        // Erst anlegen
        String response = mockMvc.perform(post("/api/policy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPolicy)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PolicyDto created = objectMapper.readValue(response, PolicyDto.class);

        // Löschen
        mockMvc.perform(delete("/api/policy/" + created.getId()))
                .andExpect(status().isNoContent());

        assertThat(policyRepository.findById(created.getId())).isEmpty();
    }
}
