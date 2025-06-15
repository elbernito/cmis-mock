package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.RelationshipDto;
import ch.elbernito.cmis.mock.service.RelationshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relationships")
@Tag(name = "Relationships", description = "CMIS Relationship operations")
public class RelationshipController {
    @Autowired
    private RelationshipService relationshipService;

    @PostMapping
    @Operation(summary = "Create relationship")
    public ResponseEntity<RelationshipDto> create(@RequestBody RelationshipDto dto) {
        return ResponseEntity.status(201).body(relationshipService.createRelationship(dto));
    }

    @GetMapping
    @Operation(summary = "List relationships by sourceId")
    public ResponseEntity<List<RelationshipDto>> list(@RequestParam String sourceId) {
        return ResponseEntity.ok(relationshipService.listRelationships(sourceId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get relationship by ID")
    public ResponseEntity<RelationshipDto> get(@PathVariable String id) {
        return ResponseEntity.ok(relationshipService.getRelationship(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete relationship")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        relationshipService.deleteRelationship(id);
        return ResponseEntity.noContent().build();
    }
}
