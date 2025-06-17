package ch.elbernito.cmis.mock.dto;

import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.time.LocalDateTime;

/**
 * DTO for CMIS Relationship.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelationshipDto {
    private Long id;
    private Long sourceDocumentId;
    private Long targetDocumentId;
    private String type;
    private String createdBy;
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
