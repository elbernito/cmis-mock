package ch.elbernito.cmis.mock.dto;

import lombok.*;

/**
 * Data Transfer Object for PolicyModel.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PolicyDto {

    private String policyId;
    private String name;
    private String description;
    private String content;
}
