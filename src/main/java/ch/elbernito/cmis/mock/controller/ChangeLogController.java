package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.ChangeLogDto;
import ch.elbernito.cmis.mock.service.ChangeLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for CMIS ChangeLog operations.
 */
@RestController
@RequestMapping("/api/crud/changelog")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "ChangeLog", description = "CMIS ChangeLog management API")
public class ChangeLogController {

    private final ChangeLogService changeLogService;

    @Operation(summary = "Get all changelog entries", responses = {@ApiResponse(responseCode = "200", description = "Entries listed")})
    @GetMapping
    public ResponseEntity<List<ChangeLogDto>> getAllEntries() {
        log.info("GET /api/crud/changelog");
        return ResponseEntity.ok(changeLogService.getAllEntries());
    }

    @Operation(summary = "Get changelog entries for objectId", responses = {@ApiResponse(responseCode = "200", description = "Entries listed")})
    @GetMapping("/object/{objectId}")
    public ResponseEntity<List<ChangeLogDto>> getEntriesByObjectId(@PathVariable String objectId) {
        log.info("GET /api/crud/changelog/object/{}", objectId);
        return ResponseEntity.ok(changeLogService.getEntriesByObjectId(objectId));
    }

    @Operation(summary = "Add new changelog entry", responses = {@ApiResponse(responseCode = "200", description = "Entry added")})
    @PostMapping
    public ResponseEntity<ChangeLogDto> addEntry(@RequestBody ChangeLogDto dto) {
        log.info("POST /api/crud/changelog");
        return ResponseEntity.ok(changeLogService.addEntry(dto));
    }
}
