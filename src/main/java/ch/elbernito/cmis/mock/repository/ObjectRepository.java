package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.ObjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for CmisObjectModel.
 */
@Repository
public interface ObjectRepository extends JpaRepository<ObjectModel, Long> {
    Optional<ObjectModel> findByObjectId(String objectId);

    Optional<ObjectModel> findByPath(String path);
}
