package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.PolicyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PolicyRepository extends JpaRepository<PolicyModel, Long> {
    Optional<PolicyModel> findByPolicyId(String policyId);
}
