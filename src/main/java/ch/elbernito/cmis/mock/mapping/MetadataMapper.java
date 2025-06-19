package ch.elbernito.cmis.mock.mapping;

import ch.elbernito.cmis.mock.dto.MetadataDto;
import ch.elbernito.cmis.mock.model.MetadataModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper for MetadataModel <-> MetadataDto.
 */
@Component
@Slf4j
public class MetadataMapper {

    public MetadataDto toDto(MetadataModel model) {
        if (model == null) {
            log.warn("MetadataModel is null in toDto()");
            return null;
        }
        return MetadataDto.builder()
                .metadataId(model.getMetadataId())
                .documentId(model.getDocumentId())
                .key(model.getKey())
                .value(model.getValue())
                .build();
    }

    public MetadataModel toEntity(MetadataDto dto) {
        if (dto == null) {
            log.warn("MetadataDto is null in toEntity()");
            return null;
        }
        return MetadataModel.builder()
                .metadataId(dto.getMetadataId())
                .documentId(dto.getDocumentId())
                .key(dto.getKey())
                .value(dto.getValue())
                .build();
    }
}
