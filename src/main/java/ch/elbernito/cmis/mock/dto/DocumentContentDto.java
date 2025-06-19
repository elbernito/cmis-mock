package ch.elbernito.cmis.mock.dto;

import lombok.*;

/**
 * DTO for document content (download/upload).
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocumentContentDto {

    private String documentId;
    private byte[] content;
    private String mimeType;
}
