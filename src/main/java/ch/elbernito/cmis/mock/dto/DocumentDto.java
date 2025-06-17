package ch.elbernito.cmis.mock.dto;

import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for CMIS Document entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDto {
    private Long id;
    private String objectId;
    private String name;
    private String typeId;
    private byte[] content;
    private long contentSize;
    private LocalDateTime creationDate;
    private String createdBy;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;
    private Long parentFolderId;
    private List<MetadataDto> metadataList;
    private List<VersionDto> versions;
    // this is not in a CMIS Spec!
    private List<PolicyDto> policies;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
