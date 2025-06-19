package ch.elbernito.cmis.mock.mapping;

import ch.elbernito.cmis.mock.dto.RetentionDto;
import ch.elbernito.cmis.mock.model.RetentionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper for RetentionModel <-> RetentionDto.
 */
@Component
@Slf4j
public class RetentionMapper {

    public RetentionDto toDto(RetentionModel model) {
        if (model == null) {
            log.warn("RetentionModel is null in toDto()");
            return null;
        }
        return RetentionDto.builder()
                .retentionId(model.getRetentionId())
                .objectId(model.getObjectId())
                .label(model.getLabel())
                .retentionUntil(model.getRetentionUntil())
                .build();
    }

    public RetentionModel toEntity(RetentionDto dto) {
        if (dto == null) {
            log.warn("RetentionDto is null in toEntity()");
            return null;
        }
        return RetentionModel.builder()
                .retentionId(dto.getRetentionId())
                .objectId(dto.getObjectId())
                .label(dto.getLabel())
                .retentionUntil(dto.getRetentionUntil())
                .build();
    }
}
