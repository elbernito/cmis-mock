package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Integration tests for DocumentController.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DocumentControllerTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void testGetAllDocuments() {
        ResponseEntity<DocumentDto[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/documents", DocumentDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<DocumentDto> list = Arrays.asList(response.getBody());
        assertTrue(list.size() >= 3);
    }

    @Test
    void testCreateAndGetDocument() {
        DocumentDto doc = DocumentDto.builder()
                .name("TestDocX")
                .parentFolderId(null)
                .content("example content".getBytes())
                .mimeType("application/pdf")
                .createdBy("tester")
                .creationDate(LocalDateTime.now())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DocumentDto> entity = new HttpEntity<>(doc, headers);

        ResponseEntity<DocumentDto> postResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/documents", entity, DocumentDto.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
        Long id = postResponse.getBody().getId();

        ResponseEntity<DocumentDto> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/documents/" + id, DocumentDto.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("TestDocX", getResponse.getBody().getName());
    }
}
