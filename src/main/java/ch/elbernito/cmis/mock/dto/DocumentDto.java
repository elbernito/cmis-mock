package ch.elbernito.cmis.mock.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for DocumentModel.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocumentDto {

    private String documentId;
    private String name;
    private String description;
    private String mimeType;
    private Long contentLength;
    private String versionLabel;
    private Boolean isLatestVersion;
    private LocalDateTime lastModifiedAt;
    private LocalDateTime createdAt;
    private String createdBy;
    private String lastModifiedBy;
    private String parentFolderId;
    private String objectId;
    private String typeId;
    private byte[] content;
}
