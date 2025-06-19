package ch.elbernito.cmis.mock.cmis;

import ch.elbernito.cmis.mock.cmis.dto.AllowableActionsDto;
import ch.elbernito.cmis.mock.cmis.dto.RepositoryInfoDto;
import ch.elbernito.cmis.mock.cmis.dto.RepositoryMetaDto;
import ch.elbernito.cmis.mock.dto.*;

import java.util.List;

/**
 * Service interface für alle gebündelten CMIS 1.2 Endpunkte.
 * Alle Methoden entsprechen exakt den Endpunkten und Aufrufen in der CmisV12MockServiceImpl.
 */
public interface CmisV12MockService {

    // 1. Repository

    /**
     * Listet alle Repositories.
     */
    List<RepositoryMetaDto> getAllRepositories();

    /**
     * Liefert Details zu einem Repository.
     */
    RepositoryMetaDto getRepository(String id);

    /**
     * Liefert Infos/Capabilities zu einem Repository.
     */
    RepositoryInfoDto getRepositoryInfo(String id);

    // 2. Object

    /**
     * Liefert ein beliebiges CMIS-Objekt (Document, Folder, etc.).
     */
    ObjectDto getObject(String objectId);

    /**
     * Liefert ein beliebiges CMIS-Objekt anhand des Pfads.
     */
    ObjectDto getObjectByPath(String path);

    /**
     * Verschiebt ein Objekt in einen Zielordner.
     */
    ObjectDto moveObject(String objectId, String targetFolderId);

    /**
     * Kopiert ein Objekt in einen Zielordner.
     */
    ObjectDto copyObject(String objectId, String targetFolderId);

    /**
     * Listet erlaubte Aktionen für ein Objekt.
     */
    AllowableActionsDto getAllowableActions(String objectId);

    /**
     * Liefert die Beziehungen zu einem Objekt.
     */
    List<RelationshipDto> getObjectRelationships(String objectId);

    // 3. Document

    /**
     * Erstellt ein neues Dokument.
     */
    DocumentDto createDocument(DocumentDto docDto);

    /**
     * Liefert ein Dokument.
     */
    DocumentDto getDocument(String documentId);

    /**
     * Aktualisiert ein Dokument.
     */
    DocumentDto updateDocument(String documentId, DocumentDto docDto);

    /**
     * Löscht ein Dokument.
     */
    void deleteDocument(String documentId);

    /**
     * Checkt ein Dokument ein.
     */
    DocumentDto checkinDocument(String documentId);

    /**
     * Checkt ein Dokument aus.
     */
    DocumentDto checkoutDocument(String documentId);

    /**
     * Liefert alle Versionen eines Dokuments.
     */
    List<VersionDto> getDocumentVersions(String documentId);

    /**
     * Liefert den Inhalt eines Dokuments.
     */
    byte[] getDocumentContent(String documentId);

    /**
     * Aktualisiert den Inhalt eines Dokuments.
     */
    void updateDocumentContent(String documentId, byte[] content, String mimeType);

    // 4. Folder

    /**
     * Erstellt einen Ordner.
     */
    FolderDto createFolder(FolderDto folderDto);

    /**
     * Liefert einen Ordner.
     */
    FolderDto getFolder(String folderId);

    /**
     * Aktualisiert einen Ordner.
     */
    FolderDto updateFolder(String folderId, FolderDto folderDto);

    /**
     * Löscht einen Ordner.
     */
    void deleteFolder(String folderId);

    /**
     * Listet die direkten Unterobjekte eines Ordners.
     */
    List<ObjectDto> getFolderChildren(String folderId);

    /**
     * Listet alle Nachkommen eines Ordners.
     */
    List<ObjectDto> getFolderDescendants(String folderId);

    /**
     * Liefert den übergeordneten Ordner.
     */
    FolderDto getFolderParent(String folderId);

    /**
     * Löscht einen kompletten Ordnerbaum.
     */
    void deleteFolderTree(String folderId);

    /**
     * Listet alle ausgecheckten Dokumente eines Ordners.
     */
    List<DocumentDto> getCheckedOutDocs(String folderId);

    // 5. Version

    /**
     * Erstellt eine einzelne Version.
     */
    public VersionDto createVersion(VersionDto versionDto);

    /**
     * Liefert eine einzelne Version.
     */
    VersionDto getVersion(String versionId);

    /**
     * Liefert alle Versionen eines Dokuments.
     */
    List<VersionDto> getVersionsForDocument(String documentId);

    // 6. Metadata

    /**
     * Erstellt ein Metadatum.
     */
    MetadataDto createMetadata(MetadataDto metadataDto);

    /**
     * Liefert ein Metadatum.
     */
    MetadataDto getMetadata(String metadataId);

    /**
     * Aktualisiert ein Metadatum.
     */
    MetadataDto updateMetadata(String metadataId, MetadataDto metadataDto);

    /**
     * Löscht ein Metadatum.
     */
    void deleteMetadata(String metadataId);

    /**
     * Listet alle Metadaten eines Dokuments.
     */
    List<MetadataDto> getMetadataByDocumentId(String documentId);

    // 7. Relationship

    /**
     * Erstellt eine Beziehung.
     */
    RelationshipDto createRelationship(RelationshipDto relationshipDto);

    /**
     * Liefert eine Beziehung.
     */
    RelationshipDto getRelationship(String relationshipId);

    /**
     * Löscht eine Beziehung.
     */
    void deleteRelationship(String relationshipId);

    /**
     * Listet alle Beziehungen eines Objekts.
     */
    List<RelationshipDto> getRelationshipsByObjectId(String objectId);

    // 8. Policy

    /**
     * Erstellt eine Policy.
     */
    PolicyDto createPolicy(PolicyDto policyDto);

    /**
     * Liefert eine Policy.
     */
    PolicyDto getPolicy(String policyId);

    /**
     * Löscht eine Policy.
     */
    void deletePolicy(String policyId);

    /**
     * Wendet eine Policy auf ein Objekt an.
     */
    void applyPolicyToObject(String objectId, String policyId);

    /**
     * Entfernt eine Policy von einem Objekt.
     */
    void removePolicyFromObject(String objectId, String policyId);

    // 9. ACL

    /**
     * Liefert die ACL eines Objekts.
     */
    List<AclDto> getAclForObject(String objectId);

    /**
     * Setzt die ACL für ein Objekt.
     */
    AclDto setAclForObject(String objectId, AclDto aclDto);

    // 10. Retention

    /**
     * Erstellt eine Retention.
     */
    RetentionDto createRetention(RetentionDto retentionDto);

    /**
     * Liefert eine Retention.
     */
    RetentionDto getRetention(String retentionId);

    /**
     * Löscht eine Retention.
     */
    void deleteRetention(String retentionId);

    /**
     * Listet alle Retentions zu einem Objekt.
     */
    List<RetentionDto> getRetentionsByObjectId(String objectId);

    // 11. ChangeLog

    /**
     * Listet alle ChangeLog-Einträge.
     */
    List<ChangeLogDto> getChangeLog();

    // 12. TypeDefinition

    /**
     * Listet alle Typen.
     */
    List<TypeDefinitionDto> getAllTypeDefinitions();

    /**
     * Liefert die Details zu einem Typ.
     */
    TypeDefinitionDto getTypeDefinition(String typeId);
}
