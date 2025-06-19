package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.RepositoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for RepositoryModel.
 */
@Repository
public interface RepositoryRepository extends JpaRepository<RepositoryModel, Long> {
    Optional<RepositoryModel> findByRepositoryId(String repositoryId);
}
