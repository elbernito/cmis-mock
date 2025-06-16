package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.VersionDto;
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
 * Integration tests for VersionController.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class VersionControllerTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void testCreateAndGetLatestVersion() {
        VersionDto version = VersionDto.builder()
                .documentId(1L)
                .versionLabel("1.0")
                .latest(true)
                .comment("Initial version")
                .content("testcontent".getBytes())
                .mimeType("application/pdf")
                .createdBy("tester")
                .creationDate(LocalDateTime.now())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<VersionDto> entity = new HttpEntity<>(version, headers);

        ResponseEntity<VersionDto> postResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/versions", entity, VersionDto.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
        Long id = postResponse.getBody().getId();

        ResponseEntity<VersionDto> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/versions/" + id, VersionDto.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("1.0", getResponse.getBody().getVersionLabel());
    }

    @Test
    void testGetVersionsByDocumentId() {
        Long documentId = 1L;
        ResponseEntity<VersionDto[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/versions/document/" + documentId, VersionDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<VersionDto> list = Arrays.asList(response.getBody());
        assertTrue(list.size() >= 0);
    }
}
