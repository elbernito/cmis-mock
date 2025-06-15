package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.ObjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Object entities.
 */
@Repository
public interface ObjectRepository extends JpaRepository<ObjectModel, String> {
}
