package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.service.FolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for CMIS Folder operations.
 */
@RestController
@RequestMapping("/api/crud/folders")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Folder", description = "CMIS Folder management API")
public class FolderController {

    private final FolderService folderService;

    @Operation(summary = "Create new folder", responses = {@ApiResponse(responseCode = "200", description = "Folder created")})
    @PostMapping
    public ResponseEntity<FolderDto> createFolder(@RequestBody FolderDto dto) {
        log.info("POST /api/crud/folders");
        return ResponseEntity.ok(folderService.createFolder(dto));
    }

    @Operation(summary = "Get folder by folderId", responses = {@ApiResponse(responseCode = "200", description = "Folder found")})
    @GetMapping("/{folderId}")
    public ResponseEntity<FolderDto> getFolder(@PathVariable String folderId) {
        log.info("GET /api/crud/folders/{}", folderId);
        return ResponseEntity.ok(folderService.getFolder(folderId));
    }

    @Operation(summary = "Update folder by folderId", responses = {@ApiResponse(responseCode = "200", description = "Folder updated")})
    @PutMapping("/{folderId}")
    public ResponseEntity<FolderDto> updateFolder(@PathVariable String folderId, @RequestBody FolderDto dto) {
        log.info("PUT /api/crud/folders/{}", folderId);
        return ResponseEntity.ok(folderService.updateFolder(folderId, dto));
    }

    @Operation(summary = "Delete folder by folderId", responses = {@ApiResponse(responseCode = "204", description = "Folder deleted")})
    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteFolder(@PathVariable String folderId) {
        log.info("DELETE /api/crud/folders/{}", folderId);
        folderService.deleteFolder(folderId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get children of folder", responses = {@ApiResponse(responseCode = "200", description = "Children listed")})
    @GetMapping("/{folderId}/children")
    public ResponseEntity<List<FolderDto>> getChildren(@PathVariable String folderId) {
        log.info("GET /api/crud/folders/{}/children", folderId);
        return ResponseEntity.ok(folderService.getChildren(folderId));
    }

    @Operation(summary = "Get descendants (folder tree)", responses = {@ApiResponse(responseCode = "200", description = "Descendants listed")})
    @GetMapping("/{folderId}/descendants")
    public ResponseEntity<List<FolderDto>> getDescendants(@PathVariable String folderId) {
        log.info("GET /api/crud/folders/{}/descendants", folderId);
        return ResponseEntity.ok(folderService.getDescendants(folderId));
    }

    @Operation(summary = "Get parent of folder", responses = {@ApiResponse(responseCode = "200", description = "Parent found")})
    @GetMapping("/{folderId}/parent")
    public ResponseEntity<FolderDto> getParent(@PathVariable String folderId) {
        log.info("GET /api/crud/folders/{}/parent", folderId);
        return ResponseEntity.ok(folderService.getParent(folderId));
    }

    @Operation(summary = "Delete folder tree", responses = {@ApiResponse(responseCode = "204", description = "Folder tree deleted")})
    @DeleteMapping("/{folderId}/tree")
    public ResponseEntity<Void> deleteTree(@PathVariable String folderId) {
        log.info("DELETE /api/crud/folders/{}/tree", folderId);
        folderService.deleteTree(folderId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get checked-out docs in folder", responses = {@ApiResponse(responseCode = "200", description = "Checked-out docs listed")})
    @GetMapping("/{folderId}/checkedout")
    public ResponseEntity<List<String>> getCheckedOutDocs(@PathVariable String folderId) {
        log.info("GET /api/crud/folders/{}/checkedout", folderId);
        return ResponseEntity.ok(folderService.getCheckedOutDocs(folderId));
    }
}
