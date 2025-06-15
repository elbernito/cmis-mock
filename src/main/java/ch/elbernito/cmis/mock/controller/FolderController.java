package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.service.FolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folders")
@Tag(name = "Folders", description = "CMIS Folder operations")
public class FolderController {
    @Autowired
    private FolderService folderService;

    @PostMapping
    @Operation(summary = "Create a new folder")
    public ResponseEntity<FolderDto> create(@RequestBody FolderDto dto) {
        return ResponseEntity.status(201).body(folderService.createFolder(dto));
    }

    @GetMapping
    @Operation(summary = "List folders with paging")
    public ResponseEntity<List<FolderDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(folderService.listFolders(page, size).getContent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get folder by ID")
    public ResponseEntity<FolderDto> get(@PathVariable String id) {
        return ResponseEntity.ok(folderService.getFolder(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update folder")
    public ResponseEntity<FolderDto> update(
            @PathVariable String id,
            @RequestBody FolderDto dto) {
        return ResponseEntity.ok(folderService.updateFolder(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete folder")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        folderService.deleteFolder(id);
        return ResponseEntity.noContent().build();
    }
}
