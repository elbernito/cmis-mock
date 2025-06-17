package ch.elbernito.cmis.mock.dto;

import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for CMIS Folder entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderDto {
    private Long id;
    private String objectId;
    private String name;
    private String typeId;
    private LocalDateTime creationDate;
    private String createdBy;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;
    private Long parentFolderId; // Null für Root
    private List<Long> childrenIds;
    private List<Long> documentIds;
    // this is not in a CMIS Spec!
    private List<PolicyDto> policies;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
