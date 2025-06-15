package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.PolicyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Policy entities.
 */
@Repository
public interface PolicyRepository extends JpaRepository<PolicyModel, String> {
}
