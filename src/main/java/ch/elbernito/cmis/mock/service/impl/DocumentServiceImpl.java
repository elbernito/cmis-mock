package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.DocumentContentDto;
import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.exception.DocumentNotFoundException;
import ch.elbernito.cmis.mock.mapping.DocumentMapper;
import ch.elbernito.cmis.mock.model.ChangeType;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.model.VersionModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.VersionRepository;
import ch.elbernito.cmis.mock.service.ChangeLogService;
import ch.elbernito.cmis.mock.service.DocumentService;
import ch.elbernito.cmis.mock.util.VersionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of DocumentService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final VersionRepository versionRepository;
    private final DocumentMapper documentMapper;

    private final ChangeLogService changeLogService;

    @Override
    public DocumentDto createDocument(DocumentDto documentDto) {
        log.info("Creating document: {}", documentDto);
        DocumentModel entity = documentMapper.toEntity(documentDto);
        entity.setVersionLabel(VersionUtil.getNextVersion("new"));
        entity.setIsLatestVersion(true);
        log.info("New document entity for save: {}", entity);

        // 1. Dokument speichern
        DocumentModel saved = documentRepository.save(entity);

        // 2. Neue Version anlegen
        VersionModel version = new VersionModel();
        version.setVersionLabel(saved.getVersionLabel());
        version.setObjectId(saved.getDocumentId());
        version.setDocument(saved); // Beziehung setzen
        version.setIsLatestVersion(true);
        version.setDocument(entity);
        version.setCreationDate(LocalDateTime.now());

        // 3. Version speichern
        versionRepository.save(version);

        // 4. Optional: Dokument mit Version aktualisieren
        if (null == saved.getVersions()) {
            log.info("No saved versions for document objectId: {}", saved.getObjectId());
            List<VersionModel> versions = new ArrayList<>();
            versions.add(version);
            saved.setVersions(versions);
        } else {
            log.info("Found saved versions for document objectId: {}", saved.getObjectId());
            saved.getVersions().add(version);
        }

        documentRepository.save(saved);

        // ChangeLog-Eintrag
        changeLogService.logChange(
                saved.getDocumentId(),
                ChangeType.CREATED,
                "Document created: " + saved.getName()
        );

        return documentMapper.toDto(saved);
    }

    @Override
    public DocumentDto getDocument(String documentId) {
        log.info("Fetching document by ID: {}", documentId);
        DocumentModel model = documentRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found: " + documentId));
        return documentMapper.toDto(model);
    }

    @Override
    public DocumentDto updateDocument(String documentId, DocumentDto documentDto) {
        log.info("Updating document: {}", documentId);
        DocumentModel existing = documentRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found: " + documentId));
        existing.setDocumentId(documentId);
        existing.setName(documentDto.getName());
        existing.setMimeType(documentDto.getMimeType());
        existing.setParentFolderId(documentDto.getParentFolderId());
        existing.setTypeId(documentDto.getTypeId());
        existing.setLastModifiedAt(documentDto.getLastModifiedAt());
        existing.setLastModifiedBy(documentDto.getLastModifiedBy());
        existing.setDescription(documentDto.getDescription());
        existing.setVersionLabel(VersionUtil.getNextVersion(existing.getVersionLabel()));
        existing.setIsLatestVersion(true);

        // version
        VersionModel version = new VersionModel();
        version.setVersionLabel(existing.getVersionLabel());
        version.setObjectId(existing.getObjectId());
        version.setDocument(existing); // Beziehung setzen
        version.setIsLatestVersion(true);
        version.setDocument(existing);
        version.setCreationDate(LocalDateTime.now());
        version.setObjectId(existing.getObjectId());

        versionRepository.save(version);
        if (null == existing.getVersions()) {
            List<VersionModel> versions = new ArrayList<>();
            versions.add(version);
            existing.setVersions(versions);
        } else {
            existing.getVersions().add(version);
        }


        DocumentModel saved = documentRepository.save(existing);

        changeLogService.logChange(
                documentId,
                ChangeType.UPDATED,
                "Document updated: " + saved.getName()
        );

        return documentMapper.toDto(saved);
    }

    @Override
    public void deleteDocument(String documentId) {
        log.info("Deleting document: {}", documentId);
        DocumentModel existing = documentRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found: " + documentId));
        documentRepository.delete(existing);

        changeLogService.logChange(
                documentId,
                ChangeType.DELETED,
                "Document deleted"
        );
    }

    @Override
    public List<DocumentDto> getDocumentVersions(String documentId) {
        log.info("Getting versions for document: {}", documentId);
        DocumentModel document = documentRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found: " + documentId));
        String objectId = document.getObjectId();
        List<DocumentModel> all = documentRepository.findAllByObjectId(objectId);
        List<DocumentDto> list = new ArrayList<>();
        for (DocumentModel model : all) {
            list.add(documentMapper.toDto(model));
        }
        return list;
    }


    @Override
    public DocumentContentDto downloadContent(String documentId) {
        log.info("Downloading content for document: {}", documentId);
        DocumentModel model = documentRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found: " + documentId));
        return DocumentContentDto.builder()
                .documentId(documentId)
                .content(model.getContent())
                .mimeType(model.getMimeType())
                .build();
    }

    @Override
    public DocumentDto uploadContent(String documentId, DocumentContentDto contentDto) {
        log.info("Uploading content for document: {}", documentId);
        DocumentModel model = documentRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found: " + documentId));
        model.setContent(contentDto.getContent());
        model.setContentLength((long) (contentDto.getContent() != null ? contentDto.getContent().length : 0));
        model.setMimeType(contentDto.getMimeType());
        model.setIsLatestVersion(true);
        model.setVersionLabel(VersionUtil.getNextVersion(model.getVersionLabel()));

        // version
        VersionModel version = new VersionModel();
        version.setVersionLabel(model.getVersionLabel());
        version.setObjectId(documentId);
        version.setDocument(model); // Beziehung setzen
        version.setIsLatestVersion(true);
        version.setDocument(model);
        version.setCreationDate(LocalDateTime.now());

        versionRepository.save(version);
        if (null == model.getVersions()) {
            List<VersionModel> versions = new ArrayList<>();
            versions.add(version);
            model.setVersions(versions);
        } else {
            model.getVersions().add(version);
        }

        DocumentModel saved = documentRepository.save(model);

        changeLogService.logChange(
                documentId,
                ChangeType.UPDATED,
                "Content uploaded (" + contentDto.getMimeType() + ")"
        );

        return documentMapper.toDto(saved);
    }

    @Override
    public DocumentDto checkin(String documentId) {
        log.info("Checkin document: {}", documentId);
        DocumentModel model = documentRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found: " + documentId));
        model.setIsLatestVersion(true);
        model.setVersionLabel(VersionUtil.getNextVersion(model.getVersionLabel()));

        // version
        VersionModel version = new VersionModel();
        version.setVersionLabel(model.getVersionLabel());
        version.setDocument(model); // Beziehung setzen
        version.setIsLatestVersion(true);
        version.setDocument(model);
        version.setCreationDate(LocalDateTime.now());
        version.setObjectId(documentId);

        versionRepository.save(version);
        if (null == model.getVersions()) {
            log.info("checkin has no versions for document: {}", documentId);
            List<VersionModel> versions = new ArrayList<>();
            versions.add(version);
            model.setVersions(versions);
        } else {
            log.info("checkin has versions for document: {}", documentId);
            model.getVersions().add(version);
        }

        documentRepository.save(model);

        changeLogService.logChange(
                documentId,
                ChangeType.UPDATED,
                "Check-in performed"
        );

        return documentMapper.toDto(model);
    }

    @Override
    public DocumentDto checkout(String documentId) {
        log.info("Checkout document: {}", documentId);
        DocumentModel model = documentRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found: " + documentId));
        model.setIsLatestVersion(false);
        documentRepository.save(model);

        changeLogService.logChange(
                documentId,
                ChangeType.UPDATED,
                "Check-out performed"
        );

        return documentMapper.toDto(model);
    }

    @Override
    public List<DocumentDto> getDocumentsByParentFolderId(String folderId) {
        List<DocumentModel> models = documentRepository.findAllByParentFolderId(folderId);
        return models.stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentDto> findAllByIsLatestVersion(Boolean isLatestVersion) {
        List<DocumentModel> models = documentRepository.findAllByIsLatestVersion(isLatestVersion);
        return models.stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
    }
}
