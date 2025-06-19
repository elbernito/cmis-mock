package ch.elbernito.cmis.mock.dto;

import lombok.*;

/**
 * Data Transfer Object for TypeDefinitionModel.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TypeDefinitionDto {

    private String typeId;
    private String name;
    private String description;
    private String parentTypeId;
    private Boolean creatable;
}
