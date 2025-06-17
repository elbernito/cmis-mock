package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.VersionDto;
import ch.elbernito.cmis.mock.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/versions")
public class VersionController {

    private final Logger log = LoggerFactory.getLogger(VersionController.class);

    private final VersionService versionService;

    public VersionController(VersionService versionService) {
        this.versionService = versionService;
    }

    @PostMapping
    @Operation(summary = "Create a new version")
    public VersionDto createVersion(@RequestBody VersionDto dto) {
        log.info("REST request to create version: {}", dto);
        return versionService.createVersion(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get version by id")
    public VersionDto getVersion(@PathVariable Long id) {
        log.info("REST request to get version: {}", id);
        return versionService.getVersion(id);
    }

    @GetMapping("/document/{documentId}")
    @Operation(summary = "Get all versions for a document")
    public List<VersionDto> getVersionsByDocument(@PathVariable Long documentId) {
        log.info("REST request to get all versions for document: {}", documentId);
        return versionService.getVersionsByDocumentId(documentId);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a version")
    public VersionDto updateVersion(@PathVariable Long id, @RequestBody VersionDto dto) {
        log.info("REST request to update version: {}", id);
        return versionService.updateVersion(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a version")
    public void deleteVersion(@PathVariable Long id) {
        log.info("REST request to delete version: {}", id);
        versionService.deleteVersion(id);
    }
}
