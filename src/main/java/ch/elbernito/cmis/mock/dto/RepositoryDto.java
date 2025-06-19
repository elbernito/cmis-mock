package ch.elbernito.cmis.mock.dto;

import lombok.*;

/**
 * Data Transfer Object for RepositoryModel.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RepositoryDto {

    /**
     * Repository unique ID (UUID).
     */
    private String repositoryId;

    /**
     * Name of the repository.
     */
    private String name;

    /**
     * Description of the repository.
     */
    private String description;

    /**
     * Root folder UUID.
     */
    private String rootFolderId;

    /**
     * Capabilities information.
     */
    private String capabilities;
}
