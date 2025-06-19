package ch.elbernito.cmis.mock.dto;

import ch.elbernito.cmis.mock.model.FolderModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for FolderModel.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FolderDto {

    private String folderId;
    private String objectId;
    private String name;

    @JsonIgnore
    private FolderDto parentFolder;

//    @JsonIgnore
    private String parentFolderId;

//    @JsonIgnore
    private List<FolderDto> children;


    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
}
