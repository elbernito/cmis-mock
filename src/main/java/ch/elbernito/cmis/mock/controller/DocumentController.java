package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.DocumentContentDto;
import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for CMIS Document operations.
 */
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Document", description = "CMIS Document management API")
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "Create new document",
            responses = {@ApiResponse(responseCode = "200", description = "Document created")})
    @PostMapping
    public ResponseEntity<DocumentDto> createDocument(@RequestBody DocumentDto dto) {
        log.info("POST /api/documents");
        return ResponseEntity.ok(documentService.createDocument(dto));
    }

    @Operation(summary = "Get document by documentId",
            responses = {@ApiResponse(responseCode = "200", description = "Document found")})
    @GetMapping("/{documentId}")
    public ResponseEntity<DocumentDto> getDocument(@PathVariable String documentId) {
        log.info("GET /api/documents/{}", documentId);
        return ResponseEntity.ok(documentService.getDocument(documentId));
    }

    @Operation(summary = "Update document by documentId",
            responses = {@ApiResponse(responseCode = "200", description = "Document updated")})
    @PutMapping("/{documentId}")
    public ResponseEntity<DocumentDto> updateDocument(@PathVariable String documentId, @RequestBody DocumentDto dto) {
        log.info("PUT /api/documents/{}", documentId);
        return ResponseEntity.ok(documentService.updateDocument(documentId, dto));
    }

    @Operation(summary = "Delete document by documentId",
            responses = {@ApiResponse(responseCode = "204", description = "Document deleted")})
    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String documentId) {
        log.info("DELETE /api/documents/{}", documentId);
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Checkin document",
            responses = {@ApiResponse(responseCode = "200", description = "Document checked in")})
    @PostMapping("/{documentId}/checkin")
    public ResponseEntity<DocumentDto> checkin(@PathVariable String documentId) {
        log.info("POST /api/documents/{}/checkin", documentId);
        return ResponseEntity.ok(documentService.checkin(documentId));
    }

    @Operation(summary = "Checkout document",
            responses = {@ApiResponse(responseCode = "200", description = "Document checked out")})
    @PostMapping("/{documentId}/checkout")
    public ResponseEntity<DocumentDto> checkout(@PathVariable String documentId) {
        log.info("POST /api/documents/{}/checkout", documentId);
        return ResponseEntity.ok(documentService.checkout(documentId));
    }

    @Operation(summary = "Get versions of document",
            responses = {@ApiResponse(responseCode = "200", description = "Versions listed")})
    @GetMapping("/{documentId}/versions")
    public ResponseEntity<List<DocumentDto>> getVersions(@PathVariable String documentId) {
        log.info("GET /api/documents/{}/versions", documentId);
        return ResponseEntity.ok(documentService.getDocumentVersions(documentId));
    }

    @Operation(summary = "Download document content",
            responses = {@ApiResponse(responseCode = "200", description = "Content downloaded",
                    content = @Content(mediaType = "application/octet-stream"))})
    @GetMapping("/{documentId}/content")
    public ResponseEntity<byte[]> downloadContent(@PathVariable String documentId) {
        log.info("GET /api/documents/{}/content", documentId);
        DocumentContentDto contentDto = documentService.downloadContent(documentId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documentId + "\"")
                .contentType(MediaType.parseMediaType(contentDto.getMimeType() != null ? contentDto.getMimeType() : "application/octet-stream"))
                .body(contentDto.getContent());
    }

    @Operation(summary = "Upload document content",
            responses = {@ApiResponse(responseCode = "200", description = "Content uploaded")})
    @PostMapping("/{documentId}/content")
    public ResponseEntity<DocumentDto> uploadContent(
            @PathVariable String documentId,
            @RequestBody DocumentContentDto contentDto) {
        log.info("POST /api/documents/{}/content", documentId);
        return ResponseEntity.ok(documentService.uploadContent(documentId, contentDto));
    }


    @Operation(
            summary = "Get all documents for a folder",
            description = "Returns all documents that have the given parentFolderId."
    )
    @GetMapping("/parent/{folderId}")
    public ResponseEntity<List<DocumentDto>> getDocumentsByParentFolderId(
            @Parameter(description = "ID of the parent folder") @PathVariable String folderId) {
        List<DocumentDto> docs = documentService.getDocumentsByParentFolderId(folderId);
        return ResponseEntity.ok(docs);
    }


}
