package ch.elbernito.cmis.mock.cmis;

import ch.elbernito.cmis.mock.dto.*;
import ch.elbernito.cmis.mock.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * cmis: createDocument
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
     * cmis: getDocument
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
     * cmis: getContentStream
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
     * cmis: updateDocument
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
     * cmis: deleteDocument
     * Deletes a document.
     */
    @DeleteMapping("/documents/{id}")
    @Operation(summary = "cmis:deleteDocument", description = "Delete a document")
    public ResponseEntity<Void> deleteDocument(@PathVariable String id) {
        log.info("cmis:deleteDocument – id={}", id);
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Folder ----------

    /**
     * cmis:createFolder
     * Creates a new folder.
     */
    @PostMapping("/folders")
    @Operation(summary = "cmis:createFolder", description = "Create folder")
    public ResponseEntity<FolderDto> createFolder(@RequestBody FolderDto dto) {
        log.info("cmis:createFolder – name={}", dto.getName());
        FolderDto created = folderService.createFolder(dto);
        log.debug("Created FolderDto: {}", created);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * cmis:getFolder
     * Retrieves a folder by ID.
     */
    @GetMapping("/folders/{id}")
    @Operation(summary = "cmis:getFolder", description = "Get folder")
    public ResponseEntity<FolderDto> getFolder(@PathVariable String id) {
        log.info("cmis:getFolder – id={}", id);
        FolderDto dto = folderService.getFolder(id);
        log.debug("Found FolderDto: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * cmis:updateFolder
     * Updates folder properties.
     */
    @PutMapping("/folders/{id}")
    @Operation(summary = "cmis:updateFolder", description = "Update folder")
    public ResponseEntity<FolderDto> updateFolder(
            @PathVariable String id,
            @RequestBody FolderDto dto) {
        log.info("cmis:updateFolder – id={}", id);
        FolderDto updated = folderService.updateFolder(id, dto);
        log.debug("Updated FolderDto: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * cmis:deleteFolder
     * Deletes a folder.
     */
    @DeleteMapping("/folders/{id}")
    @Operation(summary = "cmis:deleteFolder", description = "Delete folder")
    public ResponseEntity<Void> deleteFolder(@PathVariable String id) {
        log.info("cmis:deleteFolder – id={}", id);
        folderService.deleteFolder(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Metadata ----------

    /**
     * cmis:createMetadata
     * Adds metadata to an object.
     */
    @PostMapping("/metadata")
    @Operation(summary = "cmis:createMetadata", description = "Create metadata")
    public ResponseEntity<MetadataDto> createMetadata(@RequestBody MetadataDto dto) {
        log.info("cmis:createMetadata – property={} for object={}", dto.getPropertyName(), dto.getObjectId());
        MetadataDto created = metadataService.createMetadata(dto);
        log.debug("Created MetadataDto: {}", created);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * cmis:getMetadata
     * Retrieves metadata entry by ID.
     */
    @GetMapping("/metadata/{id}")
    @Operation(summary = "cmis:getMetadata", description = "Get metadata")
    public ResponseEntity<MetadataDto> getMetadata(@PathVariable String id) {
        log.info("cmis:getMetadata – id={}", id);
        MetadataDto dto = metadataService.getMetadata(id);
        log.debug("Found MetadataDto: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * cmis:updateMetadata
     * Updates a metadata entry.
     */
    @PutMapping("/metadata/{id}")
    @Operation(summary = "cmis:updateMetadata", description = "Update metadata")
    public ResponseEntity<MetadataDto> updateMetadata(
            @PathVariable String id,
            @RequestBody MetadataDto dto) {
        log.info("cmis:updateMetadata – id={}", id);
        MetadataDto updated = metadataService.updateMetadata(id, dto);
        log.debug("Updated MetadataDto: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * cmis:deleteMetadata
     * Deletes a metadata entry.
     */
    @DeleteMapping("/metadata/{id}")
    @Operation(summary = "cmis:deleteMetadata", description = "Delete metadata")
    public ResponseEntity<Void> deleteMetadata(@PathVariable String id) {
        log.info("cmis:deleteMetadata – id={}", id);
        metadataService.deleteMetadata(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Versioning ----------

    /**
     * cmis:createVersion
     * Creates a new version of an object.
     */
    @PostMapping("/versions")
    @Operation(summary = "cmis:createVersion", description = "Create version")
    public ResponseEntity<VersionDto> createVersion(@RequestBody VersionDto dto) {
        log.info("cmis:createVersion – objectId={}", dto.getObjectId());
        VersionDto created = versioningService.createVersion(dto);
        log.debug("Created VersionDto: {}", created);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * cmis:getVersion
     * Retrieves a specific version.
     */
    @GetMapping("/versions/{id}")
    @Operation(summary = "cmis:getVersion", description = "Get version")
    public ResponseEntity<VersionDto> getVersion(@PathVariable String id) {
        log.info("cmis:getVersion – id={}", id);
        VersionDto dto = versioningService.getVersion(id);
        log.debug("Found VersionDto: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * cmis:getLatestVersion
     * Retrieves the latest version of an object.
     */
    @GetMapping("/versions/latest/{objectId}")
    @Operation(summary = "cmis:getLatestVersion", description = "Get latest version")
    public ResponseEntity<VersionDto> getLatestVersion(@PathVariable String objectId) {
        log.info("cmis:getLatestVersion – objectId={}", objectId);
        VersionDto dto = versioningService.getLatestVersion(objectId);
        log.debug("Latest VersionDto: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * cmis:deleteVersion
     * Deletes a specific version.
     */
    @DeleteMapping("/versions/{id}")
    @Operation(summary = "cmis:deleteVersion", description = "Delete version")
    public ResponseEntity<Void> deleteVersion(@PathVariable String id) {
        log.info("cmis:deleteVersion – id={}", id);
        versioningService.deleteVersion(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Policy ----------

    /**
     * cmis:applyPolicy
     * Applies a policy to an object.
     */
    @PostMapping("/policies")
    @Operation(summary = "cmis:applyPolicy", description = "Apply policy")
    public ResponseEntity<PolicyDto> applyPolicy(@RequestBody PolicyDto dto) {
        log.info("cmis:applyPolicy – policyName={}", dto.getName());
        PolicyDto created = policyService.createPolicy(dto);
        log.debug("Applied PolicyDto: {}", created);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * cmis:getPolicy
     * Retrieves a policy by ID.
     */
    @GetMapping("/policies/{id}")
    @Operation(summary = "cmis:getPolicy", description = "Get policy")
    public ResponseEntity<PolicyDto> getPolicy(@PathVariable String id) {
        log.info("cmis:getPolicy – id={}", id);
        PolicyDto dto = policyService.getPolicy(id);
        log.debug("Found PolicyDto: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * cmis:removePolicy
     * Removes a policy.
     */
    @DeleteMapping("/policies/{id}")
    @Operation(summary = "cmis:removePolicy", description = "Remove policy")
    public ResponseEntity<Void> removePolicy(@PathVariable String id) {
        log.info("cmis:removePolicy – id={}", id);
        policyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Retention ----------

    /**
     * cmis:createRetention
     * Creates a retention entry.
     */
    @PostMapping("/retention")
    @Operation(summary = "cmis:createRetention", description = "Create retention")
    public ResponseEntity<RetentionDto> createRetention(@RequestBody RetentionDto dto) {
        log.info("cmis:createRetention – objectId={}", dto.getObjectId());
        RetentionDto created = retentionService.createRetention(dto);
        log.debug("Created RetentionDto: {}", created);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * cmis:cancelRetention
     * Deletes a retention entry.
     */
    @DeleteMapping("/retention/{id}")
    @Operation(summary = "cmis:cancelRetention", description = "Cancel retention")
    public ResponseEntity<Void> cancelRetention(@PathVariable String id) {
        log.info("cmis:cancelRetention – id={}", id);
        retentionService.deleteRetention(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- ACL ----------

    /**
     * cmis:applyAcl
     * Applies an ACL entry to an object.
     */
    @PostMapping("/acls")
    @Operation(summary = "cmis:applyAcl", description = "Apply ACL entry")
    public ResponseEntity<AclDto> applyAcl(@RequestBody AclDto dto) {
        log.info("cmis:applyAcl – principal={}", dto.getPrincipal());
        AclDto created = aclService.createAcl(dto);
        log.debug("Created AclDto: {}", created);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * cmis:getAcl
     * Retrieves an ACL entry.
     */
    @GetMapping("/acls/{id}")
    @Operation(summary = "cmis:getAcl", description = "Get ACL entry")
    public ResponseEntity<AclDto> getAcl(@PathVariable String id) {
        log.info("cmis:getAcl – id={}", id);
        AclDto dto = aclService.getAcl(id);
        log.debug("Found AclDto: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * cmis:removeAcl
     * Deletes an ACL entry.
     */
    @DeleteMapping("/acls/{id}")
    @Operation(summary = "cmis:removeAcl", description = "Remove ACL entry")
    public ResponseEntity<Void> removeAcl(@PathVariable String id) {
        log.info("cmis:removeAcl – id={}", id);
        aclService.deleteAcl(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Relationship ----------

    /**
     * cmis:createRelationship
     * Creates a relationship between objects.
     */
    @PostMapping("/relationships")
    @Operation(summary = "cmis:createRelationship", description = "Create relationship")
    public ResponseEntity<RelationshipDto> createRelationship(@RequestBody RelationshipDto dto) {
        log.info("cmis:createRelationship – source={}, target={}", dto.getSourceId(), dto.getTargetId());
        RelationshipDto created = relationshipService.createRelationship(dto);
        log.debug("Created RelationshipDto: {}", created);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * cmis:getRelationship
     * Retrieves a relationship by ID.
     */
    @GetMapping("/relationships/{id}")
    @Operation(summary = "cmis:getRelationship", description = "Get relationship")
    public ResponseEntity<RelationshipDto> getRelationship(@PathVariable String id) {
        log.info("cmis:getRelationship – id={}", id);
        RelationshipDto dto = relationshipService.getRelationship(id);
        log.debug("Found RelationshipDto: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * cmis:deleteRelationship
     * Deletes a relationship.
     */
    @DeleteMapping("/relationships/{id}")
    @Operation(summary = "cmis:deleteRelationship", description = "Delete relationship")
    public ResponseEntity<Void> deleteRelationship(@PathVariable String id) {
        log.info("cmis:deleteRelationship – id={}", id);
        relationshipService.deleteRelationship(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Object & Type Definitions ----------

    /**
     * cmis:createObject
     * Creates a generic CMIS object.
     */
    @PostMapping("/objects")
    @Operation(summary = "cmis:createObject", description = "Create CMIS object")
    public ResponseEntity<ObjectDto> createObject(@RequestBody ObjectDto dto) {
        log.info("cmis:createObject – type={}", dto.getType());
        ObjectDto created = objectService.createObject(dto);
        log.debug("Created ObjectDto: {}", created);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * cmis:getObject
     * Retrieves a CMIS object by ID.
     */
    @GetMapping("/objects/{id}")
    @Operation(summary = "cmis:getObject", description = "Get CMIS object")
    public ResponseEntity<ObjectDto> getObject(@PathVariable String id) {
        log.info("cmis:getObject – id={}", id);
        ObjectDto dto = objectService.getObject(id);
        log.debug("Found ObjectDto: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * cmis:deleteObject
     * Deletes a CMIS object.
     */
    @DeleteMapping("/objects/{id}")
    @Operation(summary = "cmis:deleteObject", description = "Delete CMIS object")
    public ResponseEntity<Void> deleteObject(@PathVariable String id) {
        log.info("cmis:deleteObject – id={}", id);
        objectService.deleteObject(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * cmis:getTypeDefinition
     * Retrieves a type definition by ID.
     */
    @GetMapping("/typeDefinitions/{id}")
    @Operation(summary = "cmis:getTypeDefinition", description = "Get type definition")
    public ResponseEntity<TypeDefinitionDto> getTypeDefinition(@PathVariable String id) {
        log.info("cmis:getTypeDefinition – id={}", id);
        TypeDefinitionDto dto = typeDefService.getTypeDefinition(id);
        log.debug("Found TypeDefinitionDto: {}", dto);
        return ResponseEntity.ok(dto);
    }

    // ---------- Statistics ----------

    /**
     * cmis:getRepositoryStatistics
     * Lists repository statistics.
     */
    @GetMapping("/statistics")
    @Operation(summary = "cmis:getRepositoryStatistics", description = "List statistics")
    public ResponseEntity<List<StatisticsDto>> listStatistics() {
        log.info("cmis:getRepositoryStatistics");
        List<StatisticsDto> stats = statisticsService.listStatistics();
        log.debug("Statistics count: {}", stats.size());
        return ResponseEntity.ok(stats);
    }
}

