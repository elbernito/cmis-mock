package ch.elbernito.cmis.mock.mapping;

import ch.elbernito.cmis.mock.dto.PolicyDto;
import ch.elbernito.cmis.mock.model.PolicyModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper for PolicyModel <-> PolicyDto.
 */
@Component
@Slf4j
public class PolicyMapper {

    public PolicyDto toDto(PolicyModel model) {
        if (model == null) {
            log.warn("PolicyModel is null in toDto()");
            return null;
        }
        return PolicyDto.builder()
                .policyId(model.getPolicyId())
                .name(model.getName())
                .description(model.getDescription())
                .content(model.getContent())
                .build();
    }

    public PolicyModel toEntity(PolicyDto dto) {
        if (dto == null) {
            log.warn("PolicyDto is null in toEntity()");
            return null;
        }
        return PolicyModel.builder()
                .policyId(dto.getPolicyId())
                .name(dto.getName())
                .description(dto.getDescription())
                .content(dto.getContent())
                .build();
    }
}
