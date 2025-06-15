package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.VersionDto;
import ch.elbernito.cmis.mock.service.VersioningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/versions")
@Tag(name = "Versions", description = "CMIS Versioning operations")
public class VersioningController {
    @Autowired
    private VersioningService versioningService;

    @PostMapping
    @Operation(summary = "Create a new version")
    public ResponseEntity<VersionDto> create(@RequestBody VersionDto dto) {
        return ResponseEntity.status(201).body(versioningService.createVersion(dto));
    }

    @GetMapping
    @Operation(summary = "List versions with paging")
    public ResponseEntity<List<VersionDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(versioningService.listVersions(page, size).getContent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get version by ID")
    public ResponseEntity<VersionDto> get(@PathVariable String id) {
        return ResponseEntity.ok(versioningService.getVersion(id));
    }

    @GetMapping("/{objectId}/latest")
    @Operation(summary = "Get latest version for an object")
    public ResponseEntity<VersionDto> latest(@PathVariable String objectId) {
        return ResponseEntity.ok(versioningService.getLatestVersion(objectId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete version")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        versioningService.deleteVersion(id);
        return ResponseEntity.noContent().build();
    }
}
