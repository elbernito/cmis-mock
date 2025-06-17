package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.MetadataDto;
import ch.elbernito.cmis.mock.exception.CmisNotFoundException;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.model.FolderModel;
import ch.elbernito.cmis.mock.model.MetadataModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.FolderRepository;
import ch.elbernito.cmis.mock.repository.MetadataRepository;
import ch.elbernito.cmis.mock.service.MetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of MetadataService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MetadataServiceImpl implements MetadataService {

    private final MetadataRepository metadataRepository;
    private final DocumentRepository documentRepository;
    private final FolderRepository folderRepository;

    @Override
    public MetadataDto getMetadataById(Long id) {
        log.info("Fetching metadata by id={}", id);
        MetadataModel model = metadataRepository.findById(id)
                .orElseThrow(() -> new CmisNotFoundException("Metadata not found for id=" + id));
        return toDto(model);
    }

    @Override
    public List<MetadataDto> getAllMetadata() {
        log.info("Fetching all metadata");
        List<MetadataDto> result = new ArrayList<>();
        for (MetadataModel model : metadataRepository.findAll()) {
            result.add(toDto(model));
        }
        return result;
    }

    @Override
    public List<MetadataDto> getMetadataByDocumentId(Long documentId) {
        log.info("Fetching metadata by documentId={}", documentId);
        List<MetadataDto> result = new ArrayList<>();
        for (MetadataModel model : metadataRepository.findByDocument_Id(documentId)) {
            result.add(toDto(model));
        }
        return result;
    }

    @Override
    public List<MetadataDto> getMetadataByFolderId(Long folderId) {
        log.info("Fetching metadata by folderId={}", folderId);
        List<MetadataDto> result = new ArrayList<>();
        for (MetadataModel model : metadataRepository.findByFolder_Id(folderId)) {
            result.add(toDto(model));
        }
        return result;
    }

    @Override
    public MetadataDto createMetadata(MetadataDto dto) {
        log.info("Creating metadata: {}", dto);
        MetadataModel model = new MetadataModel();
        model.setPropertyKey(dto.getPropertyKey());
        model.setPropertyValue(dto.getPropertyValue());
        model.setCreatedBy(dto.getCreatedBy());
        model.setCreatedAt(dto.getCreatedAt());

        if (dto.getDocumentId() != null) {
            DocumentModel doc = documentRepository.findById(dto.getDocumentId())
                    .orElseThrow(() -> new CmisNotFoundException("Document not found for id=" + dto.getDocumentId()));
            model.setDocument(doc);
        }
        if (dto.getFolderId() != null) {
            FolderModel folder = folderRepository.findById(dto.getFolderId())
                    .orElseThrow(() -> new CmisNotFoundException("Folder not found for id=" + dto.getFolderId()));
            model.setFolder(folder);
        }
        MetadataModel saved = metadataRepository.save(model);
        return toDto(saved);
    }

    @Override
    public MetadataDto updateMetadata(Long id, MetadataDto dto) {
        log.info("Updating metadata id={}: {}", id, dto);
        MetadataModel model = metadataRepository.findById(id)
                .orElseThrow(() -> new CmisNotFoundException("Metadata not found for id=" + id));
        model.setPropertyKey(dto.getPropertyKey());
        model.setPropertyValue(dto.getPropertyValue());
        model.setCreatedBy(dto.getCreatedBy());
        model.setCreatedAt(dto.getCreatedAt());

        if (dto.getDocumentId() != null) {
            DocumentModel doc = documentRepository.findById(dto.getDocumentId())
                    .orElseThrow(() -> new CmisNotFoundException("Document not found for id=" + dto.getDocumentId()));
            model.setDocument(doc);
        } else {
            model.setDocument(null);
        }
        if (dto.getFolderId() != null) {
            FolderModel folder = folderRepository.findById(dto.getFolderId())
                    .orElseThrow(() -> new CmisNotFoundException("Folder not found for id=" + dto.getFolderId()));
            model.setFolder(folder);
        } else {
            model.setFolder(null);
        }
        MetadataModel saved = metadataRepository.save(model);
        return toDto(saved);
    }

    @Override
    public void deleteMetadata(Long id) {
        log.info("Deleting metadata id={}", id);
        if (!metadataRepository.existsById(id)) {
            throw new CmisNotFoundException("Metadata not found for id=" + id);
        }
        metadataRepository.deleteById(id);
    }

    @Override
    public void deleteAllMetadata() {
        log.info("Deleting all metadata");
        metadataRepository.deleteAll();
    }

    public static MetadataDto toDto(MetadataModel model) {
        Long documentId = (model.getDocument() != null) ? model.getDocument().getId() : null;
        Long folderId = (model.getFolder() != null) ? model.getFolder().getId() : null;
        return MetadataDto.builder()
                .id(model.getId())
                .propertyKey(model.getPropertyKey())
                .propertyValue(model.getPropertyValue())
                .documentId(documentId)
                .folderId(folderId)
                .createdBy(model.getCreatedBy())
                .createdAt(model.getCreatedAt())
                .build();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
