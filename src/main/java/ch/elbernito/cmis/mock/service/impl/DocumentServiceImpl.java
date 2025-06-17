package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.exception.CmisNotFoundException;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of DocumentService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public DocumentDto getDocumentById(Long id) {
        log.info("Fetching document by id={}", id);
        DocumentModel model = documentRepository.findById(id)
                .orElseThrow(() -> new CmisNotFoundException("Document not found for id=" + id));
        return toDto(model);
    }

    @Override
    public DocumentDto getDocumentByObjectId(String objectId) {
        log.info("Fetching document by objectId={}", objectId);
        DocumentModel model = documentRepository.findByObjectId(objectId);
        if (model == null) {
            throw new CmisNotFoundException("Document not found for objectId=" + objectId);
        }
        return toDto(model);
    }

    @Override
    public List<DocumentDto> getAllDocuments() {
        log.info("Fetching all documents");
        List<DocumentDto> result = new ArrayList<>();
        for (DocumentModel model : documentRepository.findAll()) {
            result.add(toDto(model));
        }
        return result;
    }

    @Override
    public DocumentDto createDocument(DocumentDto dto) {
        log.info("Creating document: {}", dto);
        DocumentModel model = new DocumentModel();
        model.setObjectId(dto.getObjectId());
        model.setName(dto.getName());
        model.setTypeId(dto.getTypeId());
        model.setCreationDate(dto.getCreationDate());
        model.setCreatedBy(dto.getCreatedBy());
        model.setLastModifiedDate(dto.getLastModifiedDate());
        model.setLastModifiedBy(dto.getLastModifiedBy());
        DocumentModel saved = documentRepository.save(model);
        return toDto(saved);
    }

    @Override
    public DocumentDto updateDocument(Long id, DocumentDto dto) {
        log.info("Updating document id={}: {}", id, dto);
        DocumentModel model = documentRepository.findById(id)
                .orElseThrow(() -> new CmisNotFoundException("Document not found for id=" + id));
        model.setObjectId(dto.getObjectId());
        model.setName(dto.getName());
        model.setTypeId(dto.getTypeId());
        model.setCreationDate(dto.getCreationDate());
        model.setCreatedBy(dto.getCreatedBy());
        model.setLastModifiedDate(dto.getLastModifiedDate());
        model.setLastModifiedBy(dto.getLastModifiedBy());
        DocumentModel saved = documentRepository.save(model);
        return toDto(saved);
    }

    @Override
    public void deleteDocument(Long id) {
        log.info("Deleting document id={}", id);
        if (!documentRepository.existsById(id)) {
            throw new CmisNotFoundException("Document not found for id=" + id);
        }
        documentRepository.deleteById(id);
    }

    @Override
    public void deleteAllDocuments() {
        log.info("Deleting all documents");
        documentRepository.deleteAll();
    }

    private DocumentDto toDto(DocumentModel model) {
        return DocumentDto.builder()
                .id(model.getId())
                .objectId(model.getObjectId())
                .name(model.getName())
                .typeId(model.getTypeId())
                .creationDate(model.getCreationDate())
                .createdBy(model.getCreatedBy())
                .lastModifiedDate(model.getLastModifiedDate())
                .lastModifiedBy(model.getLastModifiedBy())
                .build();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
