package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.RelationshipDto;
import ch.elbernito.cmis.mock.service.RelationshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for CMIS Relationship operations.
 */
@RestController
@RequestMapping("/api/relationships")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Relationship", description = "CMIS Relationship management API")
public class RelationshipController {

    private final RelationshipService relationshipService;

    @Operation(summary = "Create relationship", responses = {@ApiResponse(responseCode = "200", description = "Relationship created")})
    @PostMapping
    public ResponseEntity<RelationshipDto> createRelationship(@RequestBody RelationshipDto dto) {
        log.info("POST /api/relationships");
        return ResponseEntity.ok(relationshipService.createRelationship(dto));
    }

    @Operation(summary = "Get relationship by relationshipId", responses = {@ApiResponse(responseCode = "200", description = "Relationship found")})
    @GetMapping("/{relationshipId}")
    public ResponseEntity<RelationshipDto> getRelationship(@PathVariable String relationshipId) {
        log.info("GET /api/relationships/{}", relationshipId);
        return ResponseEntity.ok(relationshipService.getRelationship(relationshipId));
    }

    @Operation(summary = "Delete relationship by relationshipId", responses = {@ApiResponse(responseCode = "204", description = "Relationship deleted")})
    @DeleteMapping("/{relationshipId}")
    public ResponseEntity<Void> deleteRelationship(@PathVariable String relationshipId) {
        log.info("DELETE /api/relationships/{}", relationshipId);
        relationshipService.deleteRelationship(relationshipId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all relationships for an objectId", responses = {@ApiResponse(responseCode = "200", description = "Relationships listed")})
    @GetMapping("/object/{objectId}")
    public ResponseEntity<List<RelationshipDto>> getRelationshipsByObjectId(@PathVariable String objectId) {
        log.info("GET /api/relationships/object/{}", objectId);
        return ResponseEntity.ok(relationshipService.getRelationshipsByObjectId(objectId));
    }
}
