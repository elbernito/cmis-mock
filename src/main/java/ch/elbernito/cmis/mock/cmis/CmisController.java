package ch.elbernito.cmis.mock.cmis;

import ch.elbernito.cmis.mock.dto.*;
import ch.elbernito.cmis.mock.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Unified CMIS 1.2 REST API Controller.
 * Exposes all CMIS operations under /api/v1.2/cmis.
 */
@RestController
@RequestMapping("/api/v1.2/cmis")
@Tag(name = "CMIS 1.2", description = "All CMIS 1.2 REST operations")
public class CmisController {
    private static final Logger log = LoggerFactory.getLogger(CmisController.class);

    private final DocumentService documentService;
    private final FolderService folderService;
    private final MetadataService metadataService;
    private final VersioningService versioningService;
    private final PolicyService policyService;
    private final RetentionService retentionService;
    private final AclService aclService;
    private final RelationshipService relationshipService;
    private final ObjectService objectService;
    private final TypeDefinitionService typeDefService;
    private final StatisticsService statisticsService;

    public CmisController(
            DocumentService documentService,
            FolderService folderService,
            MetadataService metadataService,
            VersioningService versioningService,
            PolicyService policyService,
            RetentionService retentionService,
            AclService aclService,
            RelationshipService relationshipService,
            ObjectService objectService,
            TypeDefinitionService typeDefService,
            StatisticsService statisticsService
    ) {
        this.documentService = documentService;
        this.folderService = folderService;
        this.metadataService = metadataService;
        this.versioningService = versioningService;
        this.policyService = policyService;
        this.retentionService = retentionService;
        this.aclService = aclService;
        this.relationshipService = relationshipService;
        this.objectService = objectService;
        this.typeDefService = typeDefService;
        this.statisticsService = statisticsService;
    }

    // ---------- Document ----------

    /**
     * cmis:createDocument
     * Uploads a new document.
     */
    @PostMapping(path = "/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "cmis:createDocument", description = "Upload a new document")
    public ResponseEntity<DocumentDto> createDocument(@RequestParam MultipartFile file) {
        log.info("cmis:createDocument – uploading {}", file.getOriginalFilename());
        DocumentDto dto = documentService.createDocument(file);
        log.debug("Created DocumentDto: {}", dto);
        return ResponseEntity.status(201).body(dto);
    }

    /**
     * cmis:getDocument
     * Retrieves document metadata by ID.
     */
    @GetMapping("/documents/{id}")
    @Operation(summary = "cmis:getDocument", description = "Get document metadata")
    public ResponseEntity<DocumentDto> getDocument(@PathVariable String id) {
        log.info("cmis:getDocument – id={}", id);
        DocumentDto dto = documentService.getDocument(id);
        log.debug("Found DocumentDto: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * cmis:getContentStream
     * Downloads the binary content of a document.
     */
    @GetMapping("/documents/{id}/content")
    @Operation(summary = "cmis:getContentStream", description = "Download document content")
    public ResponseEntity<byte[]> getContentStream(@PathVariable String id) {
        log.info("cmis:getContentStream – id={}", id);
        byte[] content = documentService.getContent(id);
        log.debug("Content length: {}", content.length);
        return ResponseEntity.ok(content);
    }

    /**
     * cmis:updateDocument
     * Replaces the content of an existing document.
     */
    @PutMapping(path = "/documents/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "cmis:updateDocument", description = "Update document content")
    public ResponseEntity<DocumentDto> updateDocument(
            @PathVariable String id,
            @RequestParam MultipartFile file) {
        log.info("cmis:updateDocument – id={}, file={}", id, file.getOriginalFilename());
        DocumentDto dto = documentService.updateDocument(id, file);
        log.debug("Updated DocumentDto: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * cmis:deleteDocument
     * Deletes a document.
     */
    @DeleteMapping("/documents/{id}")
    @Operation(summary = "cmis:deleteDocument", description = "Delete a document")
    public ResponseEntity<Void> deleteDocument(@PathVariable String id) {
        log.info("cmis:deleteDocument – id={}", id);
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    // ... (rest of methods remain unchanged, following same constructor-injected pattern)
}
