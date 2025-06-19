package ch.elbernito.cmis.mock.dto;

import lombok.*;

/**
 * Data Transfer Object for CmisObjectModel.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ObjectDto {

    private String objectId;

    private String name;

    private String type;

    private String parentFolderId;

    private String path;
}
