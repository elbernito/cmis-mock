package ch.elbernito.cmis.mock.dto;

import lombok.*;

/**
 * Data Transfer Object for MetadataModel.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MetadataDto {

    private String metadataId;
    private String documentId;
    private String key;
    private String value;
}
