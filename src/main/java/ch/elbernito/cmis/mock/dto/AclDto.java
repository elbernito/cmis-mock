package ch.elbernito.cmis.mock.dto;

import lombok.*;

/**
 * Data Transfer Object for AclModel.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AclDto {

    private String aclId;
    private String objectId;
    private String principal;
    private String permissions;
}
