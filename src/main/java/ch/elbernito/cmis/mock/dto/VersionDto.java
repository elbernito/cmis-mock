package ch.elbernito.cmis.mock.dto;

import ch.elbernito.cmis.mock.model.DocumentModel;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for VersionModel.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VersionDto {

    private String versionId;
    private String objectId;
    private String versionLabel;
    private Boolean isLatestVersion;
    private LocalDateTime creationDate;
    private DocumentModel document;
}
