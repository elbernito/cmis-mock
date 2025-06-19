package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.ChangeLogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for ChangeLogModel.
 */
@Repository
public interface ChangeLogRepository extends JpaRepository<ChangeLogModel, Long> {
    List<ChangeLogModel> findAllByObjectId(String objectId);
}
