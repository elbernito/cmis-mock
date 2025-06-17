package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.MetadataDto;
import ch.elbernito.cmis.mock.service.MetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for CMIS Metadata services.
 */
@RestController
@RequestMapping("/api/metadata")
@Tag(name = "Metadata Services", description = "CMIS 1.2 Metadata Operations")
@RequiredArgsConstructor
@Slf4j
public class MetadataController {

    private final MetadataService metadataService;

    @Operation(
        summary = "Get metadata by id",
        responses = {
            @ApiResponse(responseCode = "200", description = "Metadata found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = MetadataDto.class))),
            @ApiResponse(responseCode = "404", description = "Metadata not found", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public MetadataDto getMetadataById(@PathVariable Long id) {
        log.info("REST request to get metadata by id={}", id);
        return metadataService.getMetadataById(id);
    }

    @Operation(
        summary = "Get all metadata",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of metadata",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = MetadataDto.class)))
        }
    )
    @GetMapping
    public List<MetadataDto> getAllMetadata() {
        log.info("REST request to get all metadata");
        return metadataService.getAllMetadata();
    }

    @Operation(
        summary = "Get metadata by document id",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of metadata",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = MetadataDto.class)))
        }
    )
    @GetMapping("/document/{documentId}")
    public List<MetadataDto> getMetadataByDocumentId(@PathVariable Long documentId) {
        log.info("REST request to get metadata by documentId={}", documentId);
        return metadataService.getMetadataByDocumentId(documentId);
    }

    @Operation(
        summary = "Get metadata by folder id",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of metadata",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = MetadataDto.class)))
        }
    )
    @GetMapping("/folder/{folderId}")
    public List<MetadataDto> getMetadataByFolderId(@PathVariable Long folderId) {
        log.info("REST request to get metadata by folderId={}", folderId);
        return metadataService.getMetadataByFolderId(folderId);
    }

    @Operation(
        summary = "Create metadata",
        responses = {
            @ApiResponse(responseCode = "200", description = "Metadata created",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = MetadataDto.class)))
        }
    )
    @PostMapping
    public MetadataDto createMetadata(@RequestBody MetadataDto dto) {
        log.info("REST request to create metadata: {}", dto);
        return metadataService.createMetadata(dto);
    }

    @Operation(
        summary = "Update metadata",
        responses = {
            @ApiResponse(responseCode = "200", description = "Metadata updated",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = MetadataDto.class)))
        }
    )
    @PutMapping("/{id}")
    public MetadataDto updateMetadata(@PathVariable Long id, @RequestBody MetadataDto dto) {
        log.info("REST request to update metadata id={}: {}", id, dto);
        return metadataService.updateMetadata(id, dto);
    }

    @Operation(
        summary = "Delete metadata",
        responses = {
            @ApiResponse(responseCode = "200", description = "Metadata deleted", content = @Content)
        }
    )
    @DeleteMapping("/{id}")
    public void deleteMetadata(@PathVariable Long id) {
        log.info("REST request to delete metadata id={}", id);
        metadataService.deleteMetadata(id);
    }

    @Operation(
        summary = "Delete all metadata",
        responses = {
            @ApiResponse(responseCode = "200", description = "All metadata deleted", content = @Content)
        }
    )
    @DeleteMapping
    public void deleteAllMetadata() {
        log.info("REST request to delete all metadata");
        metadataService.deleteAllMetadata();
    }
}
