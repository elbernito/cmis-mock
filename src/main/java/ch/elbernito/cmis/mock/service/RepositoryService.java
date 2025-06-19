package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.RepositoryDto;

import java.util.List;

/**
 * Service interface for Repository management.
 */
public interface RepositoryService {

    /**
     * Returns all repositories.
     */
    List<RepositoryDto> getAllRepositories();

    /**
     * Returns a repository by its UUID.
     */
    RepositoryDto getRepositoryById(String repositoryId);

    /**
     * Returns repository info (capabilities etc.) by UUID.
     */
    String getRepositoryInfo(String repositoryId);

    /**
     * Creates a new repository.
     */
    RepositoryDto createRepository(RepositoryDto repositoryDto);

    /**
     * Deletes a repository by its UUID.
     */
    void deleteRepository(String repositoryId);
}
