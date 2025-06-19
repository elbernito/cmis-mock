package ch.elbernito.cmis.mock.mapping;

import ch.elbernito.cmis.mock.dto.RelationshipDto;
import ch.elbernito.cmis.mock.model.RelationshipModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper for RelationshipModel <-> RelationshipDto.
 */
@Component
@Slf4j
public class RelationshipMapper {

    public RelationshipDto toDto(RelationshipModel model) {
        if (model == null) {
            log.warn("RelationshipModel is null in toDto()");
            return null;
        }
        return RelationshipDto.builder()
                .relationshipId(model.getRelationshipId())
                .sourceId(model.getSourceId())
                .targetId(model.getTargetId())
                .relationshipType(model.getRelationshipType())
                .build();
    }

    public RelationshipModel toEntity(RelationshipDto dto) {
        if (dto == null) {
            log.warn("RelationshipDto is null in toEntity()");
            return null;
        }
        return RelationshipModel.builder()
                .relationshipId(dto.getRelationshipId())
                .sourceId(dto.getSourceId())
                .targetId(dto.getTargetId())
                .relationshipType(dto.getRelationshipType())
                .build();
    }
}
