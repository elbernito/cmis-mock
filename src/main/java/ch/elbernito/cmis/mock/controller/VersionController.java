package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.VersionDto;
import ch.elbernito.cmis.mock.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for CMIS Version operations.
 */
@RestController
@RequestMapping("/api/versions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Version", description = "CMIS Version management API")
public class VersionController {

    private final VersionService versionService;

    @Operation(summary = "Get version by versionId", responses = {@ApiResponse(responseCode = "200", description = "Version found")})
    @GetMapping("/{versionId}")
    public ResponseEntity<VersionDto> getVersion(@PathVariable String versionId) {
        log.info("GET /api/versions/{}", versionId);
        return ResponseEntity.ok(versionService.getVersion(versionId));
    }

    @Operation(summary = "Get all versions for a document objectId", responses = {@ApiResponse(responseCode = "200", description = "Versions listed")})
    @GetMapping("/document/{objectId}")
    public ResponseEntity<List<VersionDto>> getVersionsForDocument(@PathVariable String objectId) {
        log.info("GET /api/versions/document/{}", objectId);
        return ResponseEntity.ok(versionService.getVersionsForDocument(objectId));
    }

    @Operation(summary = "Get latest version for a document objectId", responses = {@ApiResponse(responseCode = "200", description = "Latest version found")})
    @GetMapping("/document/{objectId}/latest")
    public ResponseEntity<VersionDto> getLatestVersion(@PathVariable String objectId) {
        log.info("GET /api/versions/document/{}/latest", objectId);
        return ResponseEntity.ok(versionService.getLatestVersionForDocument(objectId));
    }
}
