package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.MetadataDto;
import ch.elbernito.cmis.mock.service.MetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for CMIS Metadata operations.
 */
@RestController
@RequestMapping("/api/metadata")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Metadata", description = "CMIS Metadata management API")
public class MetadataController {

    private final MetadataService metadataService;

    @Operation(summary = "Create new metadata", responses = {@ApiResponse(responseCode = "200", description = "Metadata created")})
    @PostMapping
    public ResponseEntity<MetadataDto> createMetadata(@RequestBody MetadataDto dto) {
        log.info("POST /api/metadata");
        return ResponseEntity.ok(metadataService.createMetadata(dto));
    }

    @Operation(summary = "Get metadata by metadataId", responses = {@ApiResponse(responseCode = "200", description = "Metadata found")})
    @GetMapping("/{metadataId}")
    public ResponseEntity<MetadataDto> getMetadata(@PathVariable String metadataId) {
        log.info("GET /api/metadata/{}", metadataId);
        return ResponseEntity.ok(metadataService.getMetadata(metadataId));
    }

    @Operation(summary = "Update metadata by metadataId", responses = {@ApiResponse(responseCode = "200", description = "Metadata updated")})
    @PutMapping("/{metadataId}")
    public ResponseEntity<MetadataDto> updateMetadata(@PathVariable String metadataId, @RequestBody MetadataDto dto) {
        log.info("PUT /api/metadata/{}", metadataId);
        return ResponseEntity.ok(metadataService.updateMetadata(metadataId, dto));
    }

    @Operation(summary = "Delete metadata by metadataId", responses = {@ApiResponse(responseCode = "204", description = "Metadata deleted")})
    @DeleteMapping("/{metadataId}")
    public ResponseEntity<Void> deleteMetadata(@PathVariable String metadataId) {
        log.info("DELETE /api/metadata/{}", metadataId);
        metadataService.deleteMetadata(metadataId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all metadata for a documentId", responses = {@ApiResponse(responseCode = "200", description = "Metadata listed")})
    @GetMapping("/document/{documentId}")
    public ResponseEntity<List<MetadataDto>> getMetadataByDocumentId(@PathVariable String documentId) {
        log.info("GET /api/metadata/document/{}", documentId);
        return ResponseEntity.ok(metadataService.getMetadataByDocumentId(documentId));
    }
}
