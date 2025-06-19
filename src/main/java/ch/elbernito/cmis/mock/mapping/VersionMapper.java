package ch.elbernito.cmis.mock.mapping;

import ch.elbernito.cmis.mock.dto.VersionDto;
import ch.elbernito.cmis.mock.model.VersionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper for VersionModel <-> VersionDto.
 */
@Component
@Slf4j
public class VersionMapper {

    public VersionDto toDto(VersionModel model) {
        if (model == null) {
            log.warn("VersionModel is null in toDto()");
            return null;
        }
        return VersionDto.builder()
                .versionId(model.getVersionId())
                .objectId(model.getObjectId())
                .versionLabel(model.getVersionLabel())
                .isLatestVersion(model.getIsLatestVersion())
                .creationDate(model.getCreationDate())
                .build();
    }

    public VersionModel toEntity(VersionDto dto) {
        if (dto == null) {
            log.warn("VersionDto is null in toEntity()");
            return null;
        }
        return VersionModel.builder()
                .versionId(dto.getVersionId())
                .objectId(dto.getObjectId())
                .versionLabel(dto.getVersionLabel())
                .isLatestVersion(dto.getIsLatestVersion())
                .creationDate(dto.getCreationDate())
                .build();
    }
}
