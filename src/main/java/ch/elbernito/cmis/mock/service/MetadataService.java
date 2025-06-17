package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.MetadataDto;

import java.util.List;

/**
 * Service interface for CMIS Metadata operations.
 */
public interface MetadataService {
    MetadataDto getMetadataById(Long id);

    List<MetadataDto> getAllMetadata();

    List<MetadataDto> getMetadataByDocumentId(Long documentId);

    List<MetadataDto> getMetadataByFolderId(Long folderId);

    MetadataDto createMetadata(MetadataDto dto);

    MetadataDto updateMetadata(Long id, MetadataDto dto);

    void deleteMetadata(Long id);

    void deleteAllMetadata();
}
