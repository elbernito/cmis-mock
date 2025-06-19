package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.RetentionDto;
import ch.elbernito.cmis.mock.service.RetentionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for CMIS Retention operations.
 */
@RestController
@RequestMapping("/api/retentions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Retention", description = "CMIS Retention management API")
public class RetentionController {

    private final RetentionService retentionService;

    @Operation(summary = "Create retention", responses = {@ApiResponse(responseCode = "200", description = "Retention created")})
    @PostMapping
    public ResponseEntity<RetentionDto> createRetention(@RequestBody RetentionDto dto) {
        log.info("POST /api/retentions");
        return ResponseEntity.ok(retentionService.createRetention(dto));
    }

    @Operation(summary = "Get retention by retentionId", responses = {@ApiResponse(responseCode = "200", description = "Retention found")})
    @GetMapping("/{retentionId}")
    public ResponseEntity<RetentionDto> getRetention(@PathVariable String retentionId) {
        log.info("GET /api/retentions/{}", retentionId);
        return ResponseEntity.ok(retentionService.getRetention(retentionId));
    }

    @Operation(summary = "Delete retention by retentionId", responses = {@ApiResponse(responseCode = "204", description = "Retention deleted")})
    @DeleteMapping("/{retentionId}")
    public ResponseEntity<Void> deleteRetention(@PathVariable String retentionId) {
        log.info("DELETE /api/retentions/{}", retentionId);
        retentionService.deleteRetention(retentionId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all retentions for an objectId", responses = {@ApiResponse(responseCode = "200", description = "Retentions listed")})
    @GetMapping("/object/{objectId}")
    public ResponseEntity<List<RetentionDto>> getRetentionsByObjectId(@PathVariable String objectId) {
        log.info("GET /api/retentions/object/{}", objectId);
        return ResponseEntity.ok(retentionService.getRetentionsByObjectId(objectId));
    }
}
