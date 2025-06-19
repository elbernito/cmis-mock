package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.DocumentContentDto;
import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.exception.DocumentNotFoundException;
import ch.elbernito.cmis.mock.mapping.DocumentMapper;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private final DocumentMapper documentMapper;

    @Override
    public DocumentDto createDocument(DocumentDto documentDto) {
        log.info("Creating document: {}", documentDto.getName());
        DocumentModel entity = documentMapper.toEntity(documentDto);
        DocumentModel saved = documentRepository.save(entity);
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
        existing.setName(documentDto.getName());
        existing.setMimeType(documentDto.getMimeType());
        existing.setParentFolderId(documentDto.getParentFolderId());
        existing.setTypeId(documentDto.getTypeId());
        existing.setVersionLabel(documentDto.getVersionLabel());
        existing.setIsLatestVersion(documentDto.getIsLatestVersion());
        existing.setLastModifiedAt(documentDto.getLastModifiedAt());
        existing.setLastModifiedBy(documentDto.getLastModifiedBy());
        existing.setDescription(documentDto.getDescription());
        DocumentModel saved = documentRepository.save(existing);
        return documentMapper.toDto(saved);
    }

    @Override
    public void deleteDocument(String documentId) {
        log.info("Deleting document: {}", documentId);
        DocumentModel existing = documentRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found: " + documentId));
        documentRepository.delete(existing);
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
        DocumentModel saved = documentRepository.save(model);
        return documentMapper.toDto(saved);
    }

    @Override
    public DocumentDto checkin(String documentId) {
        log.info("Checkin document: {}", documentId);
        DocumentModel model = documentRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found: " + documentId));
        model.setIsLatestVersion(true);
        documentRepository.save(model);
        return documentMapper.toDto(model);
    }

    @Override
    public DocumentDto checkout(String documentId) {
        log.info("Checkout document: {}", documentId);
        DocumentModel model = documentRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found: " + documentId));
        model.setIsLatestVersion(false);
        documentRepository.save(model);
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
