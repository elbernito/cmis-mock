package ch.elbernito.cmis.mock.mapping;

import ch.elbernito.cmis.mock.dto.ChangeLogDto;
import ch.elbernito.cmis.mock.model.ChangeLogModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper for ChangeLogModel <-> ChangeLogDto.
 */
@Component
@Slf4j
public class ChangeLogMapper {

    public ChangeLogDto toDto(ChangeLogModel model) {
        if (model == null) {
            log.warn("ChangeLogModel is null in toDto()");
            return null;
        }
        return ChangeLogDto.builder()
                .changelogId(model.getChangelogId())
                .objectId(model.getObjectId())
                .changeType(model.getChangeType())
                .summary(model.getSummary())
                .changeTime(model.getChangeTime())
                .build();
    }

    public ChangeLogModel toEntity(ChangeLogDto dto) {
        if (dto == null) {
            log.warn("ChangeLogDto is null in toEntity()");
            return null;
        }
        return ChangeLogModel.builder()
                .changelogId(dto.getChangelogId())
                .objectId(dto.getObjectId())
                .changeType(dto.getChangeType())
                .summary(dto.getSummary())
                .changeTime(dto.getChangeTime())
                .build();
    }
}
