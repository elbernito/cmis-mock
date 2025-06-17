package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.RelationshipDto;
import ch.elbernito.cmis.mock.service.RelationshipService;
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
 * REST controller for CMIS Relationship services.
 */
@RestController
@RequestMapping("/api/relationships")
@Tag(name = "Relationship Services", description = "CMIS 1.2 Relationship Operations")
@RequiredArgsConstructor
@Slf4j
public class RelationshipController {

    private final RelationshipService relationshipService;

    @Operation(
        summary = "Get relationship by id",
        responses = {
            @ApiResponse(responseCode = "200", description = "Relationship found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RelationshipDto.class))),
            @ApiResponse(responseCode = "404", description = "Relationship not found", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public RelationshipDto getRelationshipById(@PathVariable Long id) {
        log.info("REST request to get relationship by id={}", id);
        return relationshipService.getRelationshipById(id);
    }

    @Operation(
        summary = "Get all relationships",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of relationships",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RelationshipDto.class)))
        }
    )
    @GetMapping
    public List<RelationshipDto> getAllRelationships() {
        log.info("REST request to get all relationships");
        return relationshipService.getAllRelationships();
    }

    @Operation(
        summary = "Get relationships by source document id",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of relationships",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RelationshipDto.class)))
        }
    )
    @GetMapping("/source/{sourceId}")
    public List<RelationshipDto> getBySourceDocumentId(@PathVariable Long sourceId) {
        log.info("REST request to get relationships by sourceDocumentId={}", sourceId);
        return relationshipService.getBySourceDocumentId(sourceId);
    }

    @Operation(
        summary = "Get relationships by target document id",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of relationships",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RelationshipDto.class)))
        }
    )
    @GetMapping("/target/{targetId}")
    public List<RelationshipDto> getByTargetDocumentId(@PathVariable Long targetId) {
        log.info("REST request to get relationships by targetDocumentId={}", targetId);
        return relationshipService.getByTargetDocumentId(targetId);
    }

    @Operation(
        summary = "Create relationship",
        responses = {
            @ApiResponse(responseCode = "200", description = "Relationship created",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RelationshipDto.class)))
        }
    )
    @PostMapping
    public RelationshipDto createRelationship(@RequestBody RelationshipDto dto) {
        log.info("REST request to create relationship: {}", dto);
        return relationshipService.createRelationship(dto);
    }

    @Operation(
        summary = "Update relationship",
        responses = {
            @ApiResponse(responseCode = "200", description = "Relationship updated",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RelationshipDto.class)))
        }
    )
    @PutMapping("/{id}")
    public RelationshipDto updateRelationship(@PathVariable Long id, @RequestBody RelationshipDto dto) {
        log.info("REST request to update relationship id={}: {}", id, dto);
        return relationshipService.updateRelationship(id, dto);
    }

    @Operation(
        summary = "Delete relationship",
        responses = {
            @ApiResponse(responseCode = "200", description = "Relationship deleted", content = @Content)
        }
    )
    @DeleteMapping("/{id}")
    public void deleteRelationship(@PathVariable Long id) {
        log.info("REST request to delete relationship id={}", id);
        relationshipService.deleteRelationship(id);
    }

    @Operation(
        summary = "Delete all relationships",
        responses = {
            @ApiResponse(responseCode = "200", description = "All relationships deleted", content = @Content)
        }
    )
    @DeleteMapping
    public void deleteAllRelationships() {
        log.info("REST request to delete all relationships");
        relationshipService.deleteAllRelationships();
    }
}
