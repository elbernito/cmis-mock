package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.dto.MetadataDto;
import ch.elbernito.cmis.mock.dto.PolicyDto;
import ch.elbernito.cmis.mock.dto.VersionDto;
import ch.elbernito.cmis.mock.exception.CmisNotFoundException;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.model.MetadataModel;
import ch.elbernito.cmis.mock.model.PolicyModel;
import ch.elbernito.cmis.mock.model.VersionModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.VersionRepository;
import ch.elbernito.cmis.mock.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of DocumentService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final VersionRepository versionRepository;

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

    static DocumentDto toDto(DocumentModel model) {

        final List<MetadataDto> metadataDtos = new ArrayList<>();
        if (null != model.getMetadata()) {
            for (MetadataModel m : model.getMetadata()) {
                metadataDtos.add(MetadataServiceImpl.toDto(m));
            }
        }

        final List<PolicyDto> policyDtos = new ArrayList<>();
        if (null != model.getPolicies()) {
            for (PolicyModel policyModel : model.getPolicies()) {
                policyDtos.add(PolicyServiceImpl.toDto(policyModel));
            }
        }

        final List<VersionDto> versionDtos = new ArrayList<>();
        if (null != model.getVersions()) {
            for (VersionModel versionModel : model.getVersions()) {
                versionDtos.add(VersionServiceImpl.toDto(versionModel));
            }
        }

        return DocumentDto.builder()
                .id(model.getId())
                .objectId(model.getObjectId())
                .name(model.getName())
                .typeId(model.getTypeId())
                .creationDate(model.getCreationDate())
                .createdBy(model.getCreatedBy())
                .lastModifiedDate(model.getLastModifiedDate())
                .lastModifiedBy(model.getLastModifiedBy())
                .checkedOut(model.isCheckedOut())
                .checkedOutBy(model.getCheckedOutBy())
                .content(model.getContent())
                .contentSize(model.getContentSize())
                .metadataList(metadataDtos)
                .parentFolderId(model.getParentFolder() == null ? -1L : model.getParentFolder().getId())
                .policies(policyDtos)
                .versionLabel(model.getVersionLabel())
                .versions(versionDtos)
                .versionSeriesId(model.getVersionSeriesId())
                .contentSize(model.getContentSize())
                .build();
    }

    @Override
    public DocumentDto checkOut(Long documentId, String userId) {
        DocumentModel doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        doc.setCheckedOut(true);
        doc.setCheckedOutBy(userId);
        documentRepository.save(doc);
        return toDto(doc);
    }

    @Override
    public DocumentDto cancelCheckOut(Long documentId, String userId) {
        DocumentModel doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        if (!userId.equals(doc.getCheckedOutBy())) throw new SecurityException("Not owner!");
        doc.setCheckedOut(false);
        doc.setCheckedOutBy(null);
        documentRepository.save(doc);
        return toDto(doc);
    }

    @Override
    public DocumentDto checkIn(Long documentId, String userId, byte[] newContent, String comment) {
        DocumentModel doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        if (!doc.isCheckedOut()) throw new IllegalStateException("Not checked out!");
        doc.setContent(newContent);
        doc.setCheckedOut(false);
        doc.setCheckedOutBy(null);
        // Optional: Version-Logik, z.B. neue Version speichern
        documentRepository.save(doc);
        return toDto(doc);
    }

    @Override
    public List<VersionDto> getAllVersions(Long documentId) {
        final List<VersionModel> versionModels = versionRepository.findByDocumentId(documentId);
        List<VersionDto> result = new ArrayList<>();
        for (VersionModel model : versionModels) {
            result.add(VersionServiceImpl.toDto(model));
        }
        return result;
    }

    @Override
    public byte[] getContentStream(Long documentId) {
        DocumentModel doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        return doc.getContent();
    }

    @Override
    public void setContentStream(Long documentId, byte[] content) {
        DocumentModel doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        doc.setContent(content);
        documentRepository.save(doc);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
