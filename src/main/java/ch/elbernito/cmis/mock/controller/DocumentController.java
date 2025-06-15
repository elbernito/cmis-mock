package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@Tag(name = "Documents", description = "CMIS Document operations")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @PostMapping
    @Operation(summary = "Upload a new document")
    public ResponseEntity<DocumentDto> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(201).body(documentService.createDocument(file));
    }

    @GetMapping
    @Operation(summary = "List documents with paging")
    public ResponseEntity<List<DocumentDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(documentService.listDocuments(page, size).getContent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get document metadata by ID")
    public ResponseEntity<DocumentDto> get(@PathVariable String id) {
        return ResponseEntity.ok(documentService.getDocument(id));
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "Download document content")
    public ResponseEntity<byte[]> download(@PathVariable String id) {
        return ResponseEntity.ok(documentService.getContent(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update document content")
    public ResponseEntity<DocumentDto> update(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(documentService.updateDocument(id, file));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete document by ID")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
