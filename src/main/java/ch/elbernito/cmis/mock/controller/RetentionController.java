package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.RetentionDto;
import ch.elbernito.cmis.mock.service.RetentionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retention")
@Tag(name = "Retention", description = "CMIS Retention operations")
public class RetentionController {
    @Autowired
    private RetentionService retentionService;

    @PostMapping
    @Operation(summary = "Create a retention entry")
    public ResponseEntity<RetentionDto> create(@RequestBody RetentionDto dto) {
        return ResponseEntity.status(201).body(retentionService.createRetention(dto));
    }

    @GetMapping
    @Operation(summary = "List retention entries with paging")
    public ResponseEntity<List<RetentionDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(retentionService.listRetentions(page, size).getContent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get retention by ID")
    public ResponseEntity<RetentionDto> get(@PathVariable String id) {
        return ResponseEntity.ok(retentionService.getRetention(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete retention")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        retentionService.deleteRetention(id);
        return ResponseEntity.noContent().build();
    }
}
