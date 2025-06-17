package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.RetentionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RetentionRepository extends JpaRepository<RetentionModel, String> {
    List<RetentionModel> findByObjectId(String objectId);
}
