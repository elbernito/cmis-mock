package ch.elbernito.cmis.mock.dto;

import lombok.*;

/**
 * Data Transfer Object for RelationshipModel.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RelationshipDto {

    private String relationshipId;
    private String sourceId;
    private String targetId;
    private String relationshipType;
}
