package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.AclModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ACL entities.
 */
@Repository
public interface AclRepository extends JpaRepository<AclModel, String> {
}
