package ch.elbernito.cmis.mock.mapping;

import ch.elbernito.cmis.mock.dto.TypeDefinitionDto;
import ch.elbernito.cmis.mock.model.TypeDefinitionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper for TypeDefinitionModel <-> TypeDefinitionDto.
 */
@Component
@Slf4j
public class TypeDefinitionMapper {

    public TypeDefinitionDto toDto(TypeDefinitionModel model) {
        if (model == null) {
            log.warn("TypeDefinitionModel is null in toDto()");
            return null;
        }
        return TypeDefinitionDto.builder()
                .typeId(model.getTypeId())
                .name(model.getName())
                .description(model.getDescription())
                .parentTypeId(model.getParentTypeId())
                .creatable(model.getCreatable())
                .build();
    }

    public TypeDefinitionModel toEntity(TypeDefinitionDto dto) {
        if (dto == null) {
            log.warn("TypeDefinitionDto is null in toEntity()");
            return null;
        }
        return TypeDefinitionModel.builder()
                .typeId(dto.getTypeId())
                .name(dto.getName())
                .description(dto.getDescription())
                .parentTypeId(dto.getParentTypeId())
                .creatable(dto.getCreatable())
                .build();
    }
}
