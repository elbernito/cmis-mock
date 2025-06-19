package ch.elbernito.cmis.mock.cmis.impl;

import ch.elbernito.cmis.mock.cmis.CmisV12MockService;
import ch.elbernito.cmis.mock.cmis.dto.*;
import ch.elbernito.cmis.mock.dto.*;
import ch.elbernito.cmis.mock.exception.ConstraintViolationException;
import ch.elbernito.cmis.mock.exception.ObjectNotFoundException;
import ch.elbernito.cmis.mock.exception.RepositoryNotFoundException;
import ch.elbernito.cmis.mock.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * This is a controller class, for all CMIS 1.2 expected endpoints. For CRUD operations, use the
 * other api. Maybe, not all working correct. <i>Object</i> maybe not has all implmentations that
 * CMIS will. But we hope, we has not to implement...
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class CmisV12MockServiceImpl implements CmisV12MockService {

    private final RepositoryService repositoryService;
    private final DocumentService documentService;
    private final FolderService folderService;
    private final VersionService versionService;
    private final MetadataService metadataService;
    private final RelationshipService relationshipService;
    private final PolicyService policyService;
    private final AclService aclService;
    private final RetentionService retentionService;
    private final ChangeLogService changeLogService;
    private final TypeDefinitionService typeDefinitionService;
    private final ObjectService objectService;

    // 1. Repository

    @Override
    public List<RepositoryMetaDto> getAllRepositories() {
        List<RepositoryMetaDto> result = new ArrayList<>();
        for (RepositoryDto repo : repositoryService.getAllRepositories()) {
            result.add(RepositoryMetaDto.builder()
                    .id(repo.getRepositoryId())
                    .name(repo.getName())
                    .description(repo.getDescription())
                    .build());
        }
        return result;
    }

    @Override
    public RepositoryMetaDto getRepository(String id) {
        final RepositoryDto repo = repositoryService.getRepositoryById(id);
        if (repo == null)
            throw new RepositoryNotFoundException("Repository not found: " + id);
        return RepositoryMetaDto.builder()
                .id(repo.getRepositoryId())
                .name(repo.getName())
                .description(repo.getDescription())
                .build();
    }

    @Override
    public RepositoryInfoDto getRepositoryInfo(String id) {
        return RepositoryInfoDto.builder()
                .repositoryId(id)
                .productName("CMISMock")
                .productVersion("1.0")
                .cmisVersion("1.2")
                .allVersionsSearchable(true)
                .multifiling(true)
                .unfiling(true)
                .versionSpecificFiling(true)
                .pwcSearchable(true)
                .pwcUpdatable(true)
                .build();
    }

    // 2. Object

    @Override
    public ObjectDto getObject(String objectId) {
        DocumentDto doc = documentService.getDocument(objectId);
        if (doc != null) {
            return ObjectDto.builder()
                    .objectId(doc.getDocumentId())
                    .name(doc.getName())
                    .type("Document")
                    .build();
        }
        FolderDto folder = folderService.getFolder(objectId);
        if (folder != null) {
            return ObjectDto.builder()
                    .objectId(folder.getFolderId())
                    .name(folder.getName())
                    .type("Folder")
                    .build();
        }
        throw new ObjectNotFoundException("Object not found: " + objectId);
    }

    @Override
    public ObjectDto getObjectByPath(String path) {
        return objectService.getObjectByPath(path);

        }

    @Override
    public ObjectDto moveObject(String objectId, String targetFolderId) {
        DocumentDto doc = documentService.getDocument(objectId);
        folderService.getFolder(targetFolderId); // throws wenn nicht vorhanden
        doc.setParentFolderId(targetFolderId);
        DocumentDto moved = documentService.updateDocument(objectId, doc);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(objectId)
                .changeType("MOVE")
                .summary("Moved to folder: " + targetFolderId)
                .build());
        return ObjectDto.builder().objectId(moved.getDocumentId()).type("Document").build();
    }

    @Override
    public ObjectDto copyObject(String objectId, String targetFolderId) {
        DocumentDto doc = documentService.getDocument(objectId);
        folderService.getFolder(targetFolderId); // throws wenn nicht vorhanden
        DocumentDto copy = DocumentDto.builder()
                .name(doc.getName() + "_copy")
                .contentLength(doc.getContentLength())
                .mimeType(doc.getMimeType())
                .parentFolderId(targetFolderId)
                .build();
        DocumentDto created = documentService.createDocument(copy);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(created.getDocumentId())
                .changeType("COPY")
                .summary("Copied from " + objectId + " to folder: " + targetFolderId)
                .build());
        return ObjectDto.builder().objectId(created.getDocumentId()).type("Document").build();
    }

    @Override
    public AllowableActionsDto getAllowableActions(String objectId) {
        Map<String, Boolean> actions = new HashMap<>();
        actions.put("canRead", true);
        actions.put("canWrite", true);
        actions.put("canDelete", true);
        actions.put("canVersion", true);
        return AllowableActionsDto.builder()
                .objectId(objectId)
                .actions(actions)
                .build();
    }

    @Override
    public List<RelationshipDto> getObjectRelationships(String objectId) {
        return relationshipService.getRelationshipsByObjectId(objectId);
    }

    // 3. Document

    @Override
    public DocumentDto createDocument(DocumentDto docDto) {
        if (docDto.getParentFolderId() == null || docDto.getParentFolderId().isEmpty())
            throw new ConstraintViolationException("Document must be created in a folder (parentFolderId required).");
        folderService.getFolder(docDto.getParentFolderId());
        DocumentDto created = documentService.createDocument(docDto);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(created.getDocumentId())
                .changeType("CREATE")
                .summary("Document created in folder " + docDto.getParentFolderId())
                .build());
        return created;
    }

    @Override
    public DocumentDto getDocument(String documentId) {
        return documentService.getDocument(documentId);
    }

    @Override
    public DocumentDto updateDocument(String documentId, DocumentDto docDto) {
        DocumentDto updated = documentService.updateDocument(documentId, docDto);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(documentId)
                .changeType("UPDATE")
                .summary("Document updated")
                .build());
        return updated;
    }

    @Override
    public void deleteDocument(String documentId) {
        documentService.deleteDocument(documentId);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(documentId)
                .changeType("DELETE")
                .summary("Document deleted")
                .build());
    }

    @Override
    public DocumentDto checkinDocument(String documentId) {
        DocumentDto doc = documentService.checkin(documentId);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(documentId)
                .changeType("CHECKIN")
                .summary("Document checked in")
                .build());
        return doc;
    }

    @Override
    public DocumentDto checkoutDocument(String documentId) {
        DocumentDto doc = documentService.checkout(documentId);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(documentId)
                .changeType("CHECKOUT")
                .summary("Document checked out")
                .build());
        return doc;
    }

    @Override
    public List<VersionDto> getDocumentVersions(String documentId) {
        return versionService.getVersionsForDocument(documentId);
    }

    @Override
    public byte[] getDocumentContent(String documentId) {
        return documentService.downloadContent(documentId).getContent();
    }

    @Override
    public void updateDocumentContent(String documentId, byte[] content, String mimeType) {

        DocumentContentDto contentDto = documentService.downloadContent(documentId);
        contentDto.setContent(content);
        contentDto.setMimeType(mimeType);

        documentService.uploadContent(documentId, contentDto);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(documentId)
                .changeType("UPDATE_CONTENT")
                .summary("Document content updated")
                .build());
    }

    // 4. Folder

    @Override
    public FolderDto createFolder(FolderDto folderDto) {
        if (folderDto.getParentFolderId() != null && !folderDto.getParentFolderId().isEmpty()) {
            folderService.getFolder(folderDto.getParentFolderId());
        }
        FolderDto created = folderService.createFolder(folderDto);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(created.getFolderId())
                .changeType("CREATE")
                .summary("Folder created under parent " + folderDto.getParentFolderId())
                .build());
        return created;
    }

    @Override
    public FolderDto getFolder(String folderId) {
        return folderService.getFolder(folderId);
    }

    @Override
    public FolderDto updateFolder(String folderId, FolderDto folderDto) {
        FolderDto updated = folderService.updateFolder(folderId, folderDto);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(folderId)
                .changeType("UPDATE")
                .summary("Folder updated")
                .build());
        return updated;
    }

    @Override
    public void deleteFolder(String folderId) {
        folderService.deleteFolder(folderId);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(folderId)
                .changeType("DELETE")
                .summary("Folder deleted")
                .build());
    }

    @Override
    public List<ObjectDto> getFolderChildren(String folderId) {
        // Existenzprüfung (Service wirft Exception falls nicht vorhanden)
        folderService.getFolder(folderId);
        List<ObjectDto> children = new ArrayList<>();
        for (DocumentDto doc : documentService.getDocumentsByParentFolderId(folderId)) {
            children.add(ObjectDto.builder().objectId(doc.getDocumentId()).type("Document").build());
        }
        for (FolderDto folder : folderService.getChildren(folderId)) {
            children.add(ObjectDto.builder().objectId(folder.getFolderId()).type("Folder").build());
        }
        return children;
    }

    @Override
    public List<ObjectDto> getFolderDescendants(String folderId) {
        return getFolderChildren(folderId);
    }

    @Override
    public FolderDto getFolderParent(String folderId) {
        return folderService.getParent(folderId);
    }

    @Override
    public void deleteFolderTree(String folderId) {
        folderService.deleteTree(folderId);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(folderId)
                .changeType("DELETE_TREE")
                .summary("Folder tree deleted")
                .build());
    }

    @Override
    public List<DocumentDto> getCheckedOutDocs(String folderId) {
        folderService.getFolder(folderId); // throws wenn nicht vorhanden
        List<String> documentIds = folderService.getCheckedOutDocs(folderId);

        List<DocumentDto> docs = new ArrayList<>();
        for (String documentId : documentIds) {
            docs.add(documentService.getDocument(documentId));
        }
        return docs;
    }

    // 5. Version


    @Override
    public VersionDto createVersion(VersionDto versionDto) {
        return versionService.
    }

    @Override
    public VersionDto getVersion(String versionId) {
        return versionService.getVersion(versionId);
    }

    @Override
    public List<VersionDto> getVersionsForDocument(String documentId) {
        return versionService.getVersionsForDocument(documentId);
    }

    // 6. Metadata

    @Override
    public MetadataDto createMetadata(MetadataDto metadataDto) {
        documentService.getDocument(metadataDto.getDocumentId()); // throws wenn nicht vorhanden
        MetadataDto created = metadataService.createMetadata(metadataDto);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(metadataDto.getDocumentId())
                .changeType("ADD_METADATA")
                .summary("Metadata created")
                .build());
        return created;
    }

    @Override
    public MetadataDto getMetadata(String metadataId) {
        return metadataService.getMetadata(metadataId);
    }

    @Override
    public MetadataDto updateMetadata(String metadataId, MetadataDto metadataDto) {
        MetadataDto updated = metadataService.updateMetadata(metadataId, metadataDto);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(metadataDto.getDocumentId())
                .changeType("UPDATE_METADATA")
                .summary("Metadata updated")
                .build());
        return updated;
    }

    @Override
    public void deleteMetadata(String metadataId) {
        MetadataDto deleted = metadataService.getMetadata(metadataId);
        metadataService.deleteMetadata(metadataId);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(deleted.getDocumentId())
                .changeType("DELETE_METADATA")
                .summary("Metadata deleted")
                .build());
    }

    @Override
    public List<MetadataDto> getMetadataByDocumentId(String documentId) {
        documentService.getDocument(documentId); // throws wenn nicht vorhanden
        return metadataService.getMetadataByDocumentId(documentId);
    }

    // 7. Relationship

    @Override
    public RelationshipDto createRelationship(RelationshipDto relationshipDto) {
        RelationshipDto created = relationshipService.createRelationship(relationshipDto);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(created.getRelationshipId())
                .changeType("CREATE_RELATIONSHIP")
                .summary("Relationship created")
                .build());
        return created;
    }

    @Override
    public RelationshipDto getRelationship(String relationshipId) {
        return relationshipService.getRelationship(relationshipId);
    }

    @Override
    public void deleteRelationship(String relationshipId) {
        relationshipService.deleteRelationship(relationshipId);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(relationshipId)
                .changeType("DELETE_RELATIONSHIP")
                .summary("Relationship deleted")
                .build());
    }

    @Override
    public List<RelationshipDto> getRelationshipsByObjectId(String objectId) {
        return relationshipService.getRelationshipsByObjectId(objectId);
    }

    // 8. Policy

    @Override
    public PolicyDto createPolicy(PolicyDto policyDto) {
        PolicyDto created = policyService.createPolicy(policyDto);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(created.getPolicyId())
                .changeType("CREATE_POLICY")
                .summary("Policy created")
                .build());
        return created;
    }

    @Override
    public PolicyDto getPolicy(String policyId) {
        return policyService.getPolicy(policyId);
    }

    @Override
    public void deletePolicy(String policyId) {
        policyService.deletePolicy(policyId);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(policyId)
                .changeType("DELETE_POLICY")
                .summary("Policy deleted")
                .build());
    }

    @Override
    public void applyPolicyToObject(String objectId, String policyId) {
        policyService.applyPolicyToObject(objectId, policyId);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(objectId)
                .changeType("APPLY_POLICY")
                .summary("Policy applied: " + policyId)
                .build());
    }

    @Override
    public void removePolicyFromObject(String objectId, String policyId) {
        policyService.removePolicyFromObject(objectId, policyId);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(objectId)
                .changeType("REMOVE_POLICY")
                .summary("Policy removed: " + policyId)
                .build());
    }

    // 9. ACL

    @Override
    public List<AclDto> getAclForObject(String objectId) {
        return aclService.getAclForObject(objectId);
    }

    @Override
    public AclDto setAclForObject(String objectId, AclDto aclDto) {
        AclDto acl = aclService.setAclForObject(objectId, aclDto);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(objectId)
                .changeType("SET_ACL")
                .summary("ACL set/updated")
                .build());
        return acl;
    }

    // 10. Retention

    @Override
    public RetentionDto createRetention(RetentionDto retentionDto) {
        RetentionDto created = retentionService.createRetention(retentionDto);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(retentionDto.getObjectId())
                .changeType("CREATE_RETENTION")
                .summary("Retention created")
                .build());
        return created;
    }

    @Override
    public RetentionDto getRetention(String retentionId) {
        return retentionService.getRetention(retentionId);
    }

    @Override
    public void deleteRetention(String retentionId) {
        RetentionDto deleted = retentionService.getRetention(retentionId);
        retentionService.deleteRetention(retentionId);
        changeLogService.addEntry(ChangeLogDto.builder()
                .objectId(deleted.getObjectId())
                .changeType("DELETE_RETENTION")
                .summary("Retention deleted")
                .build());
    }

    @Override
    public List<RetentionDto> getRetentionsByObjectId(String objectId) {
        return retentionService.getRetentionsByObjectId(objectId);
    }

    // 11. ChangeLog

    @Override
    public List<ChangeLogDto> getChangeLog() {
        return changeLogService.getAllEntries();
    }

    // 12. TypeDefinition

    @Override
    public List<TypeDefinitionDto> getAllTypeDefinitions() {
        return typeDefinitionService.getAllTypeDefinitions();
    }

    @Override
    public TypeDefinitionDto getTypeDefinition(String typeId) {
        return typeDefinitionService.getTypeDefinition(typeId);
    }
}
