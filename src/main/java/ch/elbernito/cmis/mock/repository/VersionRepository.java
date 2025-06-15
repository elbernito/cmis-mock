package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.VersionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Version entities.
 */
@Repository
public interface VersionRepository extends JpaRepository<VersionModel, String> {
}
