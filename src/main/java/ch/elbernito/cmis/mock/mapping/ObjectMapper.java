package ch.elbernito.cmis.mock.mapping;

import ch.elbernito.cmis.mock.dto.ObjectDto;
import ch.elbernito.cmis.mock.model.ObjectModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between CmisObjectModel and CmisObjectDto.
 */
@Component
@Slf4j
public class ObjectMapper {

    public ObjectDto toDto(ObjectModel model) {
        if (model == null) {
            log.warn("CmisObjectModel is null in toDto()");
            return null;
        }
        return ObjectDto.builder()
                .objectId(model.getObjectId())
                .name(model.getName())
                .type(model.getType())
                .parentFolderId(model.getParentFolderId())
                .path(model.getPath())
                .build();
    }

    public ObjectModel toEntity(ObjectDto dto) {
        if (dto == null) {
            log.warn("CmisObjectDto is null in toEntity()");
            return null;
        }
        return ObjectModel.builder()
                .objectId(dto.getObjectId())
                .name(dto.getName())
                .type(dto.getType())
                .parentFolderId(dto.getParentFolderId())
                .path(dto.getPath())
                .build();
    }
}
