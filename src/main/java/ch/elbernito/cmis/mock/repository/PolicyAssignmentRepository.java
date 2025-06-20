package ch.elbernito.cmis.mock.repository;

import ch.elbernito.cmis.mock.model.PolicyAssignmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyAssignmentRepository extends JpaRepository<PolicyAssignmentModel, Long> {
    List<PolicyAssignmentModel> findAllByObjectId(String objectId);

    Optional<PolicyAssignmentModel> findByObjectIdAndPolicyId(String objectId, String policyId);

    void deleteByObjectIdAndPolicyId(String objectId, String policyId);
}