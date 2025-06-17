package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.dto.FolderTreeDto;
import ch.elbernito.cmis.mock.service.FolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for CMIS Folder services.
 */
@RestController
@RequestMapping("/api/folders")
@Tag(name = "Folder Services", description = "CMIS 1.2 Folder Operations")
@RequiredArgsConstructor
@Slf4j
public class FolderController {

    private final FolderService folderService;

    @Operation(
            summary = "Get folder by id",
            description = "Returns folder entry by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Folder found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FolderDto.class))),
                    @ApiResponse(responseCode = "404", description = "Folder not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public FolderDto getFolderById(@PathVariable Long id) {
        log.info("REST request to get folder by id={}", id);
        return folderService.getFolderById(id);
    }

    @Operation(
            summary = "Get folder by objectId",
            description = "Returns folder entry by objectId.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Folder found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FolderDto.class))),
                    @ApiResponse(responseCode = "404", description = "Folder not found", content = @Content)
            }
    )
    @GetMapping("/object/{objectId}")
    public FolderDto getFolderByObjectId(@PathVariable String objectId) {
        log.info("REST request to get folder by objectId={}", objectId);
        return folderService.getFolderByObjectId(objectId);
    }

    @Operation(
            summary = "Get all folders",
            description = "Returns all folders.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of folders",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FolderDto.class)))
            }
    )
    @GetMapping
    public List<FolderDto> getAllFolders() {
        log.info("REST request to get all folders");
        return folderService.getAllFolders();
    }

    @Operation(
            summary = "Get folders by parent id",
            description = "Returns all folders by parent folder id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of folders",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FolderDto.class)))
            }
    )
    @GetMapping("/parent/{parentFolderId}")
    public List<FolderDto> getFoldersByParentId(@PathVariable Long parentFolderId) {
        log.info("REST request to get folders by parentFolderId={}", parentFolderId);
        return folderService.getFoldersByParentId(parentFolderId);
    }

    @Operation(
            summary = "Create folder",
            description = "Creates a new folder entry.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Folder created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FolderDto.class)))
            }
    )
    @PostMapping
    public FolderDto createFolder(@RequestBody FolderDto dto) {
        log.info("REST request to create folder: {}", dto);
        return folderService.createFolder(dto);
    }

    @Operation(
            summary = "Update folder",
            description = "Updates a folder entry by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Folder updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FolderDto.class)))
            }
    )
    @PutMapping("/{id}")
    public FolderDto updateFolder(@PathVariable Long id, @RequestBody FolderDto dto) {
        log.info("REST request to update folder id={}: {}", id, dto);
        return folderService.updateFolder(id, dto);
    }

    @Operation(
            summary = "Delete folder",
            description = "Deletes a folder entry by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Folder deleted", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public void deleteFolder(@PathVariable Long id) {
        log.info("REST request to delete folder id={}", id);
        folderService.deleteFolder(id);
    }

    @GetMapping("/folders/{id}/children")
    public List<Object> getChildren(@PathVariable Long id) {
        return folderService.getChildren(id);
    }

    @GetMapping("/folders/{id}/tree")
    public FolderTreeDto getFolderTree(@PathVariable Long id,
                                       @RequestParam(defaultValue = "-1") int depth) {
        return folderService.getFolderTree(id, depth);
    }

    @GetMapping("/folders/{id}/parent")
    public FolderDto getParent(@PathVariable Long id) {
        return folderService.getParent(id);
    }

    @DeleteMapping("/folders/{id}/tree")
    public void deleteTree(@PathVariable Long id) {
        folderService.deleteTree(id);
    }

    @GetMapping("/folders/{id}/checkedOutDocs")
    public List<DocumentDto> getCheckedOutDocs(@PathVariable Long id) {
        return folderService.getCheckedOutDocs(id);
    }

    @Operation(
            summary = "Delete all folders",
            description = "Deletes all folder entries.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All folders deleted", content = @Content)
            }
    )
    @DeleteMapping
    public void deleteAllFolders() {
        log.info("REST request to delete all folders");
        folderService.deleteAllFolders();
    }
}
