package ch.elbernito.cmis.mock.mapping;

import ch.elbernito.cmis.mock.dto.RepositoryDto;
import ch.elbernito.cmis.mock.model.RepositoryModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between RepositoryModel and RepositoryDto.
 */
@Component
@Slf4j
public class RepositoryMapper {

    /**
     * Converts RepositoryModel to RepositoryDto.
     *
     * @param model RepositoryModel entity
     * @return RepositoryDto
     */
    public RepositoryDto toDto(RepositoryModel model) {
        if (model == null) {
            log.warn("RepositoryModel is null in toDto()");
            return null;
        }
        return RepositoryDto.builder()
                .repositoryId(model.getRepositoryId())
                .name(model.getName())
                .description(model.getDescription())
                .rootFolderId(model.getRootFolderId())
                .capabilities(model.getCapabilities())
                .build();
    }

    /**
     * Converts RepositoryDto to RepositoryModel.
     *
     * @param dto RepositoryDto
     * @return RepositoryModel entity
     */
    public RepositoryModel toEntity(RepositoryDto dto) {
        if (dto == null) {
            log.warn("RepositoryDto is null in toEntity()");
            return null;
        }
        return RepositoryModel.builder()
                .repositoryId(dto.getRepositoryId())
                .name(dto.getName())
                .description(dto.getDescription())
                .rootFolderId(dto.getRootFolderId())
                .capabilities(dto.getCapabilities())
                .build();
    }
}
