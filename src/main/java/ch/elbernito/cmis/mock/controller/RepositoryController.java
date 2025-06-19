package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.RepositoryDto;
import ch.elbernito.cmis.mock.service.RepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing CMIS Repositories.
 */
@RestController
@RequestMapping("/api/crud/repositories")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Repository", description = "CMIS Repository management API")
public class RepositoryController {

    private final RepositoryService repositoryService;

    /**
     * Returns a list of all repositories.
     */
    @Operation(
            summary = "Get all repositories",
            description = "Returns a list of all repositories.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of repositories",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RepositoryDto.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<RepositoryDto>> getAllRepositories() {
        log.info("GET /api/crud/repositories");
        List<RepositoryDto> result = repositoryService.getAllRepositories();
        return ResponseEntity.ok(result);
    }

    /**
     * Returns details for a specific repository by ID.
     */
    @Operation(
            summary = "Get repository by ID",
            description = "Returns a repository by its UUID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Repository found",
                            content = @Content(schema = @Schema(implementation = RepositoryDto.class))),
                    @ApiResponse(responseCode = "404", description = "Repository not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RepositoryDto> getRepositoryById(@PathVariable("id") String repositoryId) {
        log.info("GET /api/crud/repositories/{}", repositoryId);
        RepositoryDto result = repositoryService.getRepositoryById(repositoryId);
        return ResponseEntity.ok(result);
    }

    /**
     * Returns repository info (capabilities, etc.).
     */
    @Operation(
            summary = "Get repository info",
            description = "Returns repository info by UUID (capabilities, etc.)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Repository info"),
                    @ApiResponse(responseCode = "404", description = "Repository not found")
            }
    )
    @GetMapping("/{id}/info")
    public ResponseEntity<String> getRepositoryInfo(@PathVariable("id") String repositoryId) {
        log.info("GET /api/crud/repositories/{}/info", repositoryId);
        String info = repositoryService.getRepositoryInfo(repositoryId);
        return ResponseEntity.ok(info);
    }

    /**
     * Creates a new repository.
     */
    @Operation(
            summary = "Create repository",
            description = "Creates a new repository.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Repository created",
                            content = @Content(schema = @Schema(implementation = RepositoryDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping
    public ResponseEntity<RepositoryDto> createRepository(@RequestBody RepositoryDto dto) {
        log.info("POST /api/crud/repositories");
        RepositoryDto created = repositoryService.createRepository(dto);
        return ResponseEntity.ok(created);
    }

    /**
     * Deletes a repository by ID.
     */
    @Operation(
            summary = "Delete repository",
            description = "Deletes a repository by its UUID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Repository deleted"),
                    @ApiResponse(responseCode = "404", description = "Repository not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepository(@PathVariable("id") String repositoryId) {
        log.info("DELETE /api/crud/repositories/{}", repositoryId);
        repositoryService.deleteRepository(repositoryId);
        return ResponseEntity.noContent().build();
    }
}
