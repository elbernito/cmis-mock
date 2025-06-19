package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.MetadataDto;
import ch.elbernito.cmis.mock.exception.MetadataNotFoundException;
import ch.elbernito.cmis.mock.mapping.MetadataMapper;
import ch.elbernito.cmis.mock.model.MetadataModel;
import ch.elbernito.cmis.mock.repository.MetadataRepository;
import ch.elbernito.cmis.mock.service.MetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final MetadataMapper metadataMapper;

    @Override
    public MetadataDto createMetadata(MetadataDto metadataDto) {
        log.info("Creating metadata for documentId: {}", metadataDto.getDocumentId());
        MetadataModel entity = metadataMapper.toEntity(metadataDto);
        MetadataModel saved = metadataRepository.save(entity);
        return metadataMapper.toDto(saved);
    }

    @Override
    public MetadataDto getMetadata(String metadataId) {
        log.info("Fetching metadata by ID: {}", metadataId);
        MetadataModel model = metadataRepository.findByMetadataId(metadataId)
                .orElseThrow(() -> new MetadataNotFoundException("Metadata not found: " + metadataId));
        return metadataMapper.toDto(model);
    }

    @Override
    public MetadataDto updateMetadata(String metadataId, MetadataDto metadataDto) {
        log.info("Updating metadata: {}", metadataId);
        MetadataModel existing = metadataRepository.findByMetadataId(metadataId)
                .orElseThrow(() -> new MetadataNotFoundException("Metadata not found: " + metadataId));
        existing.setKey(metadataDto.getKey());
        existing.setValue(metadataDto.getValue());
        MetadataModel saved = metadataRepository.save(existing);
        return metadataMapper.toDto(saved);
    }

    @Override
    public void deleteMetadata(String metadataId) {
        log.info("Deleting metadata: {}", metadataId);
        MetadataModel model = metadataRepository.findByMetadataId(metadataId)
                .orElseThrow(() -> new MetadataNotFoundException("Metadata not found: " + metadataId));
        metadataRepository.delete(model);
    }

    @Override
    public List<MetadataDto> getMetadataByDocumentId(String documentId) {
        log.info("Fetching metadata for documentId: {}", documentId);
        List<MetadataModel> list = metadataRepository.findAllByDocumentId(documentId);
        List<MetadataDto> result = new ArrayList<>();
        for (MetadataModel model : list) {
            result.add(metadataMapper.toDto(model));
        }
        return result;
    }
}
