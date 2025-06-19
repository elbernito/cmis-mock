package ch.elbernito.cmis.mock.mapping;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.model.DocumentModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper for DocumentModel <-> DocumentDto.
 */
@Component
@Slf4j
public class DocumentMapper {

    public DocumentDto toDto(DocumentModel model) {
        if (model == null) {
            log.warn("DocumentModel is null in toDto()");
            return null;
        }
        return DocumentDto.builder()
                .documentId(model.getDocumentId())
                .objectId(model.getObjectId())
                .name(model.getName())
                .mimeType(model.getMimeType())
                .parentFolderId(model.getParentFolderId())
                .contentLength(model.getContentLength())
                .typeId(model.getTypeId())
                .createdBy(model.getCreatedBy())
                .lastModifiedBy(model.getLastModifiedBy())
                .description(model.getDescription())
                .content(model.getContent())
                .versionLabel(model.getVersionLabel())
                .isLatestVersion(model.getIsLatestVersion())
                .versions(model.getVersions())
                .build();
    }

    public DocumentModel toEntity(DocumentDto dto) {
        if (dto == null) {
            log.warn("DocumentDto is null in toEntity()");
            return null;
        }
        return DocumentModel.builder()
                .documentId(dto.getDocumentId())
                .objectId(dto.getObjectId())
                .name(dto.getName())
                .mimeType(dto.getMimeType())
                .parentFolderId(dto.getParentFolderId())
                .contentLength(dto.getContentLength())
                .typeId(dto.getTypeId())
                .createdBy(dto.getCreatedBy())
                .lastModifiedBy(dto.getLastModifiedBy())
                .description(dto.getDescription())
                .content(dto.getContent())
                .versionLabel(dto.getVersionLabel())
                .isLatestVersion(dto.getIsLatestVersion())
                .versions(dto.getVersions())
                .build();
    }
}
