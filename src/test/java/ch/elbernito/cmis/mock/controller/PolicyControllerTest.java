package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.PolicyDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for PolicyController.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PolicyControllerTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void testCreateAndGetPolicy() {
        PolicyDto policy = PolicyDto.builder()
                .name("SecurityPolicyX")
                .description("Test Policy for security rules.")
                .content("Allow only group X to read.")
                .type("security")
                .createdBy("admin")
                .creationDate(LocalDateTime.now())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PolicyDto> entity = new HttpEntity<>(policy, headers);

        ResponseEntity<PolicyDto> postResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/policies", entity, PolicyDto.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
        Long id = postResponse.getBody().getId();

        ResponseEntity<PolicyDto> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/policies/" + id, PolicyDto.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("SecurityPolicyX", getResponse.getBody().getName());
    }

    @Test
    void testGetAllPolicies() {
        ResponseEntity<PolicyDto[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/policies", PolicyDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<PolicyDto> list = Arrays.asList(response.getBody());
        assertTrue(list.size() >= 0);
    }
}
