package ch.elbernito.cmis.mock.example;

import ch.elbernito.cmis.mock.model.RepositoryModel;
import ch.elbernito.cmis.mock.repository.RepositoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Loads standardized CMIS Repository entries on application startup.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RepositoryInitializer {

    private final RepositoryRepository repositoryRepository;

    @PostConstruct
    public void initRepositories() {
        log.info("Loading default CMIS Repositories...");

        List<RepositoryModel> defaultRepos = List.of(
                RepositoryModel.builder()
                        .repositoryId("default-repo")
                        .name("Default Repository")
                        .description("A default example CMIS repository")
                        .rootFolderId("root-folder-1")
                        .capabilities("{\"versioning\":true,\"acl\":true,\"contentStreamUpdatable\":true}")
                        .build(),
                RepositoryModel.builder()
                        .repositoryId("test-repo")
                        .name("Test Repository")
                        .description("A secondary test CMIS repository")
                        .rootFolderId("root-folder-2")
                        .capabilities("{\"versioning\":false,\"acl\":false,\"contentStreamUpdatable\":false}")
                        .build()
        );

        for (RepositoryModel repo : defaultRepos) {
            repositoryRepository.findByRepositoryId(repo.getRepositoryId())
                .ifPresentOrElse(
                    existing -> log.debug("Repository '{}' already exists, skipping.", repo.getRepositoryId()),
                    () -> {
                        repositoryRepository.save(repo);
                        log.info("Repository '{}' created.", repo.getRepositoryId());
                    }
                );
        }

        log.info("CMIS Repositories loading finished.");
    }
}
