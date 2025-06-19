package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.RetentionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for RetentionModel.
 */
@Repository
public interface RetentionRepository extends JpaRepository<RetentionModel, Long> {

    Optional<RetentionModel> findByRetentionId(String retentionId);

    List<RetentionModel> findAllByObjectId(String objectId);
}
