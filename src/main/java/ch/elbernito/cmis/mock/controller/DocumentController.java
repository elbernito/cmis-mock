package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for CMIS Document services.
 */
@RestController
@RequestMapping("/api/documents")
@Tag(name = "Document Services", description = "CMIS 1.2 Document Operations")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final DocumentService documentService;

    @Operation(
        summary = "Get document by id",
        description = "Returns document entry by id.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Document found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class))),
            @ApiResponse(responseCode = "404", description = "Document not found", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public DocumentDto getDocumentById(@PathVariable Long id) {
        log.info("REST request to get document by id={}", id);
        return documentService.getDocumentById(id);
    }

    @Operation(
        summary = "Get document by objectId",
        description = "Returns document entry by objectId.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Document found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class))),
            @ApiResponse(responseCode = "404", description = "Document not found", content = @Content)
        }
    )
    @GetMapping("/object/{objectId}")
    public DocumentDto getDocumentByObjectId(@PathVariable String objectId) {
        log.info("REST request to get document by objectId={}", objectId);
        return documentService.getDocumentByObjectId(objectId);
    }

    @Operation(
        summary = "Get all documents",
        description = "Returns all documents.",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of documents",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class)))
        }
    )
    @GetMapping
    public List<DocumentDto> getAllDocuments() {
        log.info("REST request to get all documents");
        return documentService.getAllDocuments();
    }

    @Operation(
        summary = "Create document",
        description = "Creates a new document entry.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Document created",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class)))
        }
    )
    @PostMapping
    public DocumentDto createDocument(@RequestBody DocumentDto dto) {
        log.info("REST request to create document: {}", dto);
        return documentService.createDocument(dto);
    }

    @Operation(
        summary = "Update document",
        description = "Updates a document entry by id.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Document updated",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class)))
        }
    )
    @PutMapping("/{id}")
    public DocumentDto updateDocument(@PathVariable Long id, @RequestBody DocumentDto dto) {
        log.info("REST request to update document id={}: {}", id, dto);
        return documentService.updateDocument(id, dto);
    }

    @Operation(
        summary = "Delete document",
        description = "Deletes a document entry by id.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Document deleted", content = @Content)
        }
    )
    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable Long id) {
        log.info("REST request to delete document id={}", id);
        documentService.deleteDocument(id);
    }

    @Operation(
        summary = "Delete all documents",
        description = "Deletes all document entries.",
        responses = {
            @ApiResponse(responseCode = "200", description = "All documents deleted", content = @Content)
        }
    )
    @DeleteMapping
    public void deleteAllDocuments() {
        log.info("REST request to delete all documents");
        documentService.deleteAllDocuments();
    }
}
