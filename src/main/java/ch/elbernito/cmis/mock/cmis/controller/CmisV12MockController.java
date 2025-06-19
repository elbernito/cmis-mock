package ch.elbernito.cmis.mock.cmis.controller;

import ch.elbernito.cmis.mock.cmis.CmisV12MockService;
import ch.elbernito.cmis.mock.cmis.dto.AllowableActionsDto;
import ch.elbernito.cmis.mock.cmis.dto.RepositoryInfoDto;
import ch.elbernito.cmis.mock.cmis.dto.RepositoryMetaDto;
import ch.elbernito.cmis.mock.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for all CMIS 1.2 operations.
 * Exposes endpoints for all main and extended CMIS functions.
 */
@RestController
@RequestMapping("/api/cmis/v1.2")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "CMIS 1.2", description = "Full CMIS 1.2 REST API")
public class CmisV12MockController {

    private final CmisV12MockService cmisService;

    // --- 1. Repository ---

    @GetMapping("/repositories")
    @Operation(summary = "List all repositories", description = "Returns all repositories in the system.")
    public List<RepositoryMetaDto> getAllRepositories() {
        log.info("Fetching all repositories");
        return cmisService.getAllRepositories();
    }

    @GetMapping("/repositories/{id}")
    @Operation(summary = "Get repository details", description = "Returns details for a given repository.")
    public RepositoryMetaDto getRepository(@PathVariable String id) {
        log.info("Fetching repository: {}", id);
        return cmisService.getRepository(id);
    }

    @GetMapping("/repositories/{id}/info")
    @Operation(summary = "Get repository info", description = "Returns repository capabilities/info.")
    public RepositoryInfoDto getRepositoryInfo(@PathVariable String id) {
        log.info("Fetching repository info for: {}", id);
        return cmisService.getRepositoryInfo(id);
    }

    // --- 2. Object ---

    @GetMapping("/objects/{objectId}")
    @Operation(summary = "Get CMIS object", description = "Returns a CMIS object by objectId.")
    public ObjectDto getObject(@PathVariable String objectId) {
        log.info("Fetching object: {}", objectId);
        return cmisService.getObject(objectId);
    }

    @GetMapping("/objects/by-path")
    @Operation(summary = "Get CMIS object by path", description = "Returns a CMIS object by path.")
    public ObjectDto getObjectByPath(@RequestParam String path) {
        log.info("Fetching object by path: {}", path);
        return cmisService.getObjectByPath(path);
    }

    @PostMapping("/objects/{objectId}/move")
    @Operation(summary = "Move object", description = "Moves an object to a target folder.")
    public ObjectDto moveObject(@PathVariable String objectId, @RequestParam String targetFolderId) {
        log.info("Moving object {} to folder {}", objectId, targetFolderId);
        return cmisService.moveObject(objectId, targetFolderId);
    }

    @PostMapping("/objects/{objectId}/copy")
    @Operation(summary = "Copy object", description = "Copies an object to a target folder.")
    public ObjectDto copyObject(@PathVariable String objectId, @RequestParam String targetFolderId) {
        log.info("Copying object {} to folder {}", objectId, targetFolderId);
        return cmisService.copyObject(objectId, targetFolderId);
    }

    @GetMapping("/objects/{objectId}/allowable-actions")
    @Operation(summary = "Get allowable actions", description = "Lists allowable actions for an object.")
    public AllowableActionsDto getAllowableActions(@PathVariable String objectId) {
        log.info("Getting allowable actions for: {}", objectId);
        return cmisService.getAllowableActions(objectId);
    }

    @GetMapping("/objects/{objectId}/relationships")
    @Operation(summary = "Get object relationships", description = "Lists relationships for an object.")
    public List<RelationshipDto> getObjectRelationships(@PathVariable String objectId) {
        log.info("Getting relationships for object: {}", objectId);
        return cmisService.getObjectRelationships(objectId);
    }

    // --- 3. Document ---

    @PostMapping("/documents")
    @Operation(summary = "Create document", description = "Creates a new document.")
    public DocumentDto createDocument(@RequestBody DocumentDto docDto) {
        log.info("Creating document: {}", docDto);
        return cmisService.createDocument(docDto);
    }

    @GetMapping("/documents/{documentId}")
    @Operation(summary = "Get document", description = "Returns a document by ID.")
    public DocumentDto getDocument(@PathVariable String documentId) {
        log.info("Fetching document: {}", documentId);
        return cmisService.getDocument(documentId);
    }

    @PutMapping("/documents/{documentId}")
    @Operation(summary = "Update document", description = "Updates a document by ID.")
    public DocumentDto updateDocument(@PathVariable String documentId, @RequestBody DocumentDto docDto) {
        log.info("Updating document: {}", documentId);
        return cmisService.updateDocument(documentId, docDto);
    }

    @DeleteMapping("/documents/{documentId}")
    @Operation(summary = "Delete document", description = "Deletes a document by ID.")
    public void deleteDocument(@PathVariable String documentId) {
        log.info("Deleting document: {}", documentId);
        cmisService.deleteDocument(documentId);
    }

    @PostMapping("/documents/{documentId}/checkin")
    @Operation(summary = "Checkin document", description = "Checks in a document.")
    public DocumentDto checkinDocument(@PathVariable String documentId) {
        log.info("Checking in document: {}", documentId);
        return cmisService.checkinDocument(documentId);
    }

    @PostMapping("/documents/{documentId}/checkout")
    @Operation(summary = "Checkout document", description = "Checks out a document.")
    public DocumentDto checkoutDocument(@PathVariable String documentId) {
        log.info("Checking out document: {}", documentId);
        return cmisService.checkoutDocument(documentId);
    }

    @GetMapping("/documents/{documentId}/versions")
    @Operation(summary = "Get document versions", description = "Lists all versions for a document.")
    public List<VersionDto> getDocumentVersions(@PathVariable String documentId) {
        log.info("Getting versions for document: {}", documentId);
        return cmisService.getDocumentVersions(documentId);
    }

    @GetMapping("/documents/{documentId}/content")
    @Operation(summary = "Get document content", description = "Gets the content of a document.")
    public ResponseEntity<byte[]> getDocumentContent(@PathVariable String documentId) {
        log.info("Getting content for document: {}", documentId);
        byte[] data = cmisService.getDocumentContent(documentId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(data);
    }

    @PutMapping("/documents/{documentId}/content")
    @Operation(summary = "Update document content", description = "Updates the content of a document.")
    public void updateDocumentContent(@PathVariable String documentId, @RequestBody byte[] content,
                                      @RequestParam String mimeType) {
        log.info("Updating content for document: {}", documentId);
        cmisService.updateDocumentContent(documentId, content, mimeType);
    }

    // --- 4. Folder ---

    @PostMapping("/folders")
    @Operation(summary = "Create folder", description = "Creates a new folder.")
    public FolderDto createFolder(@RequestBody FolderDto folderDto) {
        log.info("Creating folder: {}", folderDto.getName());
        return cmisService.createFolder(folderDto);
    }

    @GetMapping("/folders/{folderId}")
    @Operation(summary = "Get folder", description = "Gets a folder by ID.")
    public FolderDto getFolder(@PathVariable String folderId) {
        log.info("Getting folder: {}", folderId);
        return cmisService.getFolder(folderId);
    }

    @PutMapping("/folders/{folderId}")
    @Operation(summary = "Update folder", description = "Updates a folder by ID.")
    public FolderDto updateFolder(@PathVariable String folderId, @RequestBody FolderDto folderDto) {
        log.info("Updating folder: {}", folderId);
        return cmisService.updateFolder(folderId, folderDto);
    }

    @DeleteMapping("/folders/{folderId}")
    @Operation(summary = "Delete folder", description = "Deletes a folder by ID.")
    public void deleteFolder(@PathVariable String folderId) {
        log.info("Deleting folder: {}", folderId);
        cmisService.deleteFolder(folderId);
    }

    @GetMapping("/folders/{folderId}/children")
    @Operation(summary = "Get folder children", description = "Lists all children of a folder.")
    public List<ObjectDto> getFolderChildren(@PathVariable String folderId) {
        log.info("Getting children for folder: {}", folderId);
        return cmisService.getFolderChildren(folderId);
    }

    @GetMapping("/folders/{folderId}/descendants")
    @Operation(summary = "Get folder descendants", description = "Lists all descendants of a folder.")
    public List<ObjectDto> getFolderDescendants(@PathVariable String folderId) {
        log.info("Getting descendants for folder: {}", folderId);
        return cmisService.getFolderDescendants(folderId);
    }

    @GetMapping("/folders/{folderId}/parent")
    @Operation(summary = "Get folder parent", description = "Gets the parent folder of a folder.")
    public FolderDto getFolderParent(@PathVariable String folderId) {
        log.info("Getting parent for folder: {}", folderId);

        FolderDto folderDto = cmisService.getFolderParent(folderId);
        log.info("Parent folder: {}", ReflectionToStringBuilder.toString(folderDto, ToStringStyle.JSON_STYLE));

        return folderDto;
    }

    @DeleteMapping("/folders/{folderId}/tree")
    @Operation(summary = "Delete folder tree", description = "Deletes a whole folder tree.")
    public void deleteFolderTree(@PathVariable String folderId) {
        log.info("Deleting folder tree: {}", folderId);
        cmisService.deleteFolderTree(folderId);
    }

    @GetMapping("/folders/{folderId}/checkedout")
    @Operation(summary = "Get checked out documents in folder", description = "Lists all checked out documents in a folder.")
    public List<DocumentDto> getCheckedOutDocs(@PathVariable String folderId) {
        log.info("Getting checked out documents for folder: {}", folderId);
        return cmisService.getCheckedOutDocs(folderId);
    }

    // --- 5. Version ---
    @GetMapping("/versions/{versionId}")
    @Operation(summary = "Get version", description = "Gets a version by ID.")
    public VersionDto getVersion(@PathVariable String versionId) {
        log.info("Getting version: {}", versionId);
        return cmisService.getVersion(versionId);
    }

    @GetMapping("/documents/{documentId}/all-versions")
    @Operation(summary = "Get all versions for document", description = "Lists all versions of a document.")
    public List<VersionDto> getVersionsForDocument(@PathVariable String documentId) {
        log.info("Getting all versions for document: {}", documentId);
        return cmisService.getVersionsForDocument(documentId);
    }

    // --- 6. Metadata ---

    @PostMapping("/metadata")
    @Operation(summary = "Create metadata", description = "Creates metadata for a document.")
    public MetadataDto createMetadata(@RequestBody MetadataDto metadataDto) {
        log.info("Creating metadata");
        return cmisService.createMetadata(metadataDto);
    }

    @GetMapping("/metadata/{metadataId}")
    @Operation(summary = "Get metadata", description = "Gets metadata by ID.")
    public MetadataDto getMetadata(@PathVariable String metadataId) {
        log.info("Getting metadata: {}", metadataId);
        return cmisService.getMetadata(metadataId);
    }

    @PutMapping("/metadata/{metadataId}")
    @Operation(summary = "Update metadata", description = "Updates metadata by ID.")
    public MetadataDto updateMetadata(@PathVariable String metadataId, @RequestBody MetadataDto metadataDto) {
        log.info("Updating metadata: {}", metadataId);
        return cmisService.updateMetadata(metadataId, metadataDto);
    }

    @DeleteMapping("/metadata/{metadataId}")
    @Operation(summary = "Delete metadata", description = "Deletes metadata by ID.")
    public void deleteMetadata(@PathVariable String metadataId) {
        log.info("Deleting metadata: {}", metadataId);
        cmisService.deleteMetadata(metadataId);
    }

    @GetMapping("/documents/{documentId}/metadata")
    @Operation(summary = "Get all metadata for document", description = "Lists all metadata entries for a document.")
    public List<MetadataDto> getMetadataByDocumentId(@PathVariable String documentId) {
        log.info("Getting metadata for document: {}", documentId);
        return cmisService.getMetadataByDocumentId(documentId);
    }

    // --- 7. Relationship ---

    @PostMapping("/relationships")
    @Operation(summary = "Create relationship", description = "Creates a new relationship.")
    public RelationshipDto createRelationship(@RequestBody RelationshipDto relationshipDto) {
        log.info("Creating relationship");
        return cmisService.createRelationship(relationshipDto);
    }

    @GetMapping("/relationships/{relationshipId}")
    @Operation(summary = "Get relationship", description = "Gets a relationship by ID.")
    public RelationshipDto getRelationship(@PathVariable String relationshipId) {
        log.info("Getting relationship: {}", relationshipId);
        return cmisService.getRelationship(relationshipId);
    }

    @DeleteMapping("/relationships/{relationshipId}")
    @Operation(summary = "Delete relationship", description = "Deletes a relationship by ID.")
    public void deleteRelationship(@PathVariable String relationshipId) {
        log.info("Deleting relationship: {}", relationshipId);
        cmisService.deleteRelationship(relationshipId);
    }

    @GetMapping("/objects/{objectId}/relationships/all")
    @Operation(summary = "Get all relationships for object", description = "Lists all relationships for a given object.")
    public List<RelationshipDto> getRelationshipsByObjectId(@PathVariable String objectId) {
        log.info("Getting all relationships for object: {}", objectId);
        return cmisService.getRelationshipsByObjectId(objectId);
    }

    // --- 8. Policy ---

    @PostMapping("/policies")
    @Operation(summary = "Create policy", description = "Creates a new policy.")
    public PolicyDto createPolicy(@RequestBody PolicyDto policyDto) {
        log.info("Creating policy");
        return cmisService.createPolicy(policyDto);
    }

    @GetMapping("/policies/{policyId}")
    @Operation(summary = "Get policy", description = "Gets a policy by ID.")
    public PolicyDto getPolicy(@PathVariable String policyId) {
        log.info("Getting policy: {}", policyId);
        return cmisService.getPolicy(policyId);
    }

    @DeleteMapping("/policies/{policyId}")
    @Operation(summary = "Delete policy", description = "Deletes a policy by ID.")
    public void deletePolicy(@PathVariable String policyId) {
        log.info("Deleting policy: {}", policyId);
        cmisService.deletePolicy(policyId);
    }

    @PostMapping("/objects/{objectId}/policies/{policyId}/apply")
    @Operation(summary = "Apply policy", description = "Applies a policy to an object.")
    public void applyPolicyToObject(@PathVariable String objectId, @PathVariable String policyId) {
        log.info("Applying policy {} to object {}", policyId, objectId);
        cmisService.applyPolicyToObject(objectId, policyId);
    }

    @PostMapping("/objects/{objectId}/policies/{policyId}/remove")
    @Operation(summary = "Remove policy", description = "Removes a policy from an object.")
    public void removePolicyFromObject(@PathVariable String objectId, @PathVariable String policyId) {
        log.info("Removing policy {} from object {}", policyId, objectId);
        cmisService.removePolicyFromObject(objectId, policyId);
    }

    // --- 9. ACL ---

    @GetMapping("/objects/{objectId}/acl")
    @Operation(summary = "Get ACL", description = "Gets the ACL for an object.")
    public List<AclDto> getAclForObject(@PathVariable String objectId) {
        log.info("Getting ACL for object: {}", objectId);
        return cmisService.getAclForObject(objectId);
    }

    @PutMapping("/objects/{objectId}/acl")
    @Operation(summary = "Set ACL", description = "Sets the ACL for an object.")
    public AclDto setAclForObject(@PathVariable String objectId, @RequestBody AclDto aclDto) {
        log.info("Setting ACL for object: {}", objectId);
        return cmisService.setAclForObject(objectId, aclDto);
    }

    // --- 10. Retention ---

    @PostMapping("/retentions")
    @Operation(summary = "Create retention", description = "Creates a new retention entry.")
    public RetentionDto createRetention(@RequestBody RetentionDto retentionDto) {
        log.info("Creating retention");
        return cmisService.createRetention(retentionDto);
    }

    @GetMapping("/retentions/{retentionId}")
    @Operation(summary = "Get retention", description = "Gets a retention by ID.")
    public RetentionDto getRetention(@PathVariable String retentionId) {
        log.info("Getting retention: {}", retentionId);
        return cmisService.getRetention(retentionId);
    }

    @DeleteMapping("/retentions/{retentionId}")
    @Operation(summary = "Delete retention", description = "Deletes a retention by ID.")
    public void deleteRetention(@PathVariable String retentionId) {
        log.info("Deleting retention: {}", retentionId);
        cmisService.deleteRetention(retentionId);
    }

    @GetMapping("/objects/{objectId}/retentions")
    @Operation(summary = "Get all retentions for object", description = "Lists all retentions for a given object.")
    public List<RetentionDto> getRetentionsByObjectId(@PathVariable String objectId) {
        log.info("Getting all retentions for object: {}", objectId);
        return cmisService.getRetentionsByObjectId(objectId);
    }

    // --- 11. ChangeLog ---

    @GetMapping("/changelog")
    @Operation(summary = "Get change log", description = "Lists all change log entries.")
    public List<ChangeLogDto> getChangeLog() {
        log.info("Getting change log");
        return cmisService.getChangeLog();
    }

    @GetMapping("/changelog/changes")
    @Operation(summary = "Get content changes", description = "Gets all content changes.")
    public List<ChangeLogDto> getContentChanges() {
        log.info("Getting content changes");
        return cmisService.getChangeLog();
    }

    // --- 12. TypeDefinition ---

    @GetMapping("/types/children")
    @Operation(summary = "Get all type definitions", description = "Get all type definitions in wonderful sweet list")
    public List<TypeDefinitionDto> getAllTypeDefinitions(@RequestParam String typeId) {
        log.info("Getting type definitions");
        return cmisService.getAllTypeDefinitions();
    }

    @GetMapping("/types/{typeId}")
    @Operation(summary = "Get type definition", description = "Gets a type definition by ID.")
    public TypeDefinitionDto getType(@PathVariable String typeId) {
        log.info("Getting type definition: {}", typeId);
        return cmisService.getTypeDefinition(typeId);
    }
}
