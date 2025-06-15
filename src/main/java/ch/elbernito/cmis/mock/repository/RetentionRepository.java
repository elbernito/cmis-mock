package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.RetentionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Retention entities.
 */
@Repository
public interface RetentionRepository extends JpaRepository<RetentionModel, String> {
}
