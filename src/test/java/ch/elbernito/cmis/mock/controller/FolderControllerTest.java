package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.FolderDto;
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
 * Integration tests for FolderController.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FolderControllerTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void testGetAllFolders() {
        ResponseEntity<FolderDto[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/folders", FolderDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<FolderDto> list = Arrays.asList(response.getBody());
        assertTrue(list.size() >= 0);
    }

    @Test
    void testCreateAndGetFolder() {
        FolderDto folder = FolderDto.builder()
                .name("TestFolderX")
                .parentFolderId(null)
                .createdBy("tester")
                .creationDate(LocalDateTime.now())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FolderDto> entity = new HttpEntity<>(folder, headers);

        ResponseEntity<FolderDto> postResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/folders", entity, FolderDto.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
        Long id = postResponse.getBody().getId();

        ResponseEntity<FolderDto> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/folders/" + id, FolderDto.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("TestFolderX", getResponse.getBody().getName());
    }
}
