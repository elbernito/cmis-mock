package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.AclDto;
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
 * Integration tests for AclController.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AclControllerTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void testCreateAndGetAcl() {
        AclDto acl = AclDto.builder()
                .objectId(1L)
                .principal("user:test")
                .permission("READ")
                .isAllowed(true)
                .createdBy("admin")
                .creationDate(LocalDateTime.now())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AclDto> entity = new HttpEntity<>(acl, headers);

        ResponseEntity<AclDto> postResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/acls", entity, AclDto.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
        Long id = postResponse.getBody().getId();

        ResponseEntity<AclDto> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/acls/" + id, AclDto.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("user:test", getResponse.getBody().getPrincipal());
    }

    @Test
    void testGetAllAcls() {
        ResponseEntity<AclDto[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/acls", AclDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<AclDto> list = Arrays.asList(response.getBody());
        assertTrue(list.size() >= 0);
    }
}
