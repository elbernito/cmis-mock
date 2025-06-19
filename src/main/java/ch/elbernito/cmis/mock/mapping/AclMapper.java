package ch.elbernito.cmis.mock.mapping;

import ch.elbernito.cmis.mock.dto.AclDto;
import ch.elbernito.cmis.mock.model.AclModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper for AclModel <-> AclDto.
 */
@Component
@Slf4j
public class AclMapper {

    public AclDto toDto(AclModel model) {
        if (model == null) {
            log.warn("AclModel is null in toDto()");
            return null;
        }
        return AclDto.builder()
                .aclId(model.getAclId())
                .objectId(model.getObjectId())
                .principal(model.getPrincipal())
                .permissions(model.getPermissions())
                .build();
    }

    public AclModel toEntity(AclDto dto) {
        if (dto == null) {
            log.warn("AclDto is null in toEntity()");
            return null;
        }
        return AclModel.builder()
                .aclId(dto.getAclId())
                .objectId(dto.getObjectId())
                .principal(dto.getPrincipal())
                .permissions(dto.getPermissions())
                .build();
    }
}
