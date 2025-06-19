package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.PolicyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for PolicyModel.
 */
@Repository
public interface PolicyRepository extends JpaRepository<PolicyModel, Long> {
    Optional<PolicyModel> findByPolicyId(String policyId);
}
