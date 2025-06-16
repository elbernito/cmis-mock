package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.MetadataDto;
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
 * Integration tests for MetadataController.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MetadataControllerTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void testGetAllMetadata() {
        ResponseEntity<MetadataDto[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/metadata", MetadataDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<MetadataDto> list = Arrays.asList(response.getBody());
        assertTrue(list.size() >= 0);
    }

    @Test
    void testCreateAndGetMetadata() {
        MetadataDto metadata = MetadataDto.builder()
                .name("author")
                .value("tester")
                .documentId(1L)
                .creationDate(LocalDateTime.now())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MetadataDto> entity = new HttpEntity<>(metadata, headers);

        ResponseEntity<MetadataDto> postResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/metadata", entity, MetadataDto.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
        Long id = postResponse.getBody().getId();

        ResponseEntity<MetadataDto> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/metadata/" + id, MetadataDto.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("author", getResponse.getBody().getName());
    }
}
