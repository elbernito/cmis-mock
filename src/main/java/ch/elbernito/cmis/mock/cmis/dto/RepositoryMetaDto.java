package ch.elbernito.cmis.mock.cmis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for repository meta-information in CMIS 1.2 mock.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryMetaDto {
    private String id;
    private String name;
    private String description;
}
