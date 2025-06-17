package ch.elbernito.cmis.mock.dto;

import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;

/**
 * DTO for Metadata entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetadataDto {
    private Long id;
    private String propertyKey;
    private String propertyValue;
    private Long documentId;
    private Long folderId;
    private String createdBy;
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
