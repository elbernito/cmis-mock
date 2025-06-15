package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.MetadataDto;
import ch.elbernito.cmis.mock.service.MetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metadata")
@Tag(name = "Metadata", description = "CMIS Metadata operations")
public class MetadataController {
    @Autowired
    private MetadataService metadataService;

    @PostMapping
    @Operation(summary = "Create metadata entry")
    public ResponseEntity<MetadataDto> create(@RequestBody MetadataDto dto) {
        return ResponseEntity.status(201).body(metadataService.createMetadata(dto));
    }

    @GetMapping
    @Operation(summary = "List metadata entries with paging")
    public ResponseEntity<List<MetadataDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(metadataService.listMetadata(page, size).getContent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get metadata by ID")
    public ResponseEntity<MetadataDto> get(@PathVariable String id) {
        return ResponseEntity.ok(metadataService.getMetadata(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update metadata entry")
    public ResponseEntity<MetadataDto> update(
            @PathVariable String id,
            @RequestBody MetadataDto dto) {
        return ResponseEntity.ok(metadataService.updateMetadata(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete metadata entry")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        metadataService.deleteMetadata(id);
        return ResponseEntity.noContent().build();
    }
}
