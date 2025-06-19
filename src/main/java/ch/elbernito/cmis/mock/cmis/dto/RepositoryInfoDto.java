package ch.elbernito.cmis.mock.cmis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for detailed repository info (e.g. CMIS capabilities).
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryInfoDto {
    private String repositoryId;
    private String productName;
    private String productVersion;
    private String cmisVersion;
    private boolean allVersionsSearchable;
    private boolean multifiling;
    private boolean unfiling;
    private boolean versionSpecificFiling;
    private boolean pwcSearchable;
    private boolean pwcUpdatable;
    // Du kannst hier beliebig viele CMIS Capabilities-Felder ergänzen!
}
