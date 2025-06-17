package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.ChangeLogModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChangeLogRepository extends JpaRepository<ChangeLogModel, Long> {
    List<ChangeLogModel> findByObjectId(String objectId);
}
