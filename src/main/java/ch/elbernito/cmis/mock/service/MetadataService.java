package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.MetadataDto;

import java.util.List;

/**
 * Service interface for Metadata management.
 */
public interface MetadataService {

    MetadataDto createMetadata(MetadataDto metadataDto);

    MetadataDto getMetadata(String metadataId);

    MetadataDto updateMetadata(String metadataId, MetadataDto metadataDto);

    void deleteMetadata(String metadataId);

    List<MetadataDto> getMetadataByDocumentId(String documentId);
}
