package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.MetadataDto;
import org.springframework.data.domain.Page;

public interface MetadataService {
    MetadataDto createMetadata(MetadataDto dto);
    Page<MetadataDto> listMetadata(int page, int size);
    MetadataDto getMetadata(String id);
    MetadataDto updateMetadata(String id, MetadataDto dto);
    void deleteMetadata(String id);
}
