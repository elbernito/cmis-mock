package ch.elbernito.cmis.mock.mapping;

import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.model.FolderModel;
import ch.elbernito.cmis.mock.repository.FolderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class FolderMapper {

    private final FolderRepository folderRepository;

    public FolderDto toDto(FolderModel model) {
        if (model == null) {
            log.warn("FolderModel is null in toDto()");
            return null;
        }

        return FolderDto.builder()
                .folderId(model.getFolderId())
                .objectId(model.getObjectId())
                .name(model.getName())
                .parentFolderId(model.getParentFolder() != null ? model.getParentFolder().getFolderId() : null)
                .children(null) // NICHT rekursiv die Kinder mappen, sondern explizit in Services!
                .creationDate(model.getCreationDate())
                .lastModifiedDate(model.getLastModifiedDate())
                .build();
    }

    public FolderModel toEntity(FolderDto dto) {
        if (dto == null) {
            log.warn("FolderDto is null in toEntity()");
            return null;
        }

        FolderModel.FolderModelBuilder builder = FolderModel.builder()
                .folderId(dto.getFolderId())
                .objectId(dto.getObjectId())
                .name(dto.getName())
                .creationDate(dto.getCreationDate())
                .lastModifiedDate(dto.getLastModifiedDate());

        // Parent Folder setzen, falls vorhanden
        if (dto.getParentFolderId() != null) {
            folderRepository.findByFolderId(dto.getParentFolderId())
                    .ifPresent(builder::parentFolder);
        }

        return builder.build();
    }
}
