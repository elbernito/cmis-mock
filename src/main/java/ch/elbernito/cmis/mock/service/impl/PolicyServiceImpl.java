package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.PolicyDto;
import ch.elbernito.cmis.mock.exception.PolicyNotFoundException;
import ch.elbernito.cmis.mock.mapping.PolicyMapper;
import ch.elbernito.cmis.mock.model.ChangeType;
import ch.elbernito.cmis.mock.model.PolicyAssignmentModel;
import ch.elbernito.cmis.mock.model.PolicyModel;
import ch.elbernito.cmis.mock.repository.PolicyAssignmentRepository;
import ch.elbernito.cmis.mock.repository.PolicyRepository;
import ch.elbernito.cmis.mock.service.ChangeLogService;
import ch.elbernito.cmis.mock.service.PolicyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of PolicyService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;
    private final PolicyMapper policyMapper;

    private final ChangeLogService changeLogService;
    private final PolicyAssignmentRepository policyAssignmentRepository;

    @Override
    public PolicyDto createPolicy(PolicyDto policyDto) {
        log.info("Creating policy: {}", policyDto.getName());
        PolicyModel entity = policyMapper.toEntity(policyDto);
        PolicyModel saved = policyRepository.save(entity);

        // ChangeLog
        changeLogService.logChange(
                saved.getPolicyId(),
                ChangeType.CREATED,
                "Policy created: " + saved.getName()
        );


        return policyMapper.toDto(saved);
    }

    @Override
    public PolicyDto getPolicy(String policyId) {
        log.info("Fetching policy by ID: {}", policyId);
        PolicyModel model = policyRepository.findByPolicyId(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found: " + policyId));
        return policyMapper.toDto(model);
    }

    @Override
    public void deletePolicy(String policyId) {
        log.info("Deleting policy: {}", policyId);
        PolicyModel model = policyRepository.findByPolicyId(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found: " + policyId));
        policyRepository.delete(model);

        // ChangeLog
        changeLogService.logChange(
                policyId,
                ChangeType.DELETED,
                "Policy deleted: " + model.getName()
        );

    }

    @Override
    public List<PolicyDto> getAllPolicies() {
        log.info("Fetching all policies");
        List<PolicyModel> list = policyRepository.findAll();
        List<PolicyDto> result = new ArrayList<>();
        for (PolicyModel model : list) {
            result.add(policyMapper.toDto(model));
        }
        return result;
    }

    @Override
    public void applyPolicyToObject(String objectId, String policyId) {
        // Check ob die Policy schon zugewiesen ist, sonst hinzufügen
        boolean exists = policyAssignmentRepository
                .findByObjectIdAndPolicyId(objectId, policyId)
                .isPresent();
        if (!exists) {
            PolicyAssignmentModel assignment = PolicyAssignmentModel.builder()
                    .objectId(objectId)
                    .policyId(policyId)
                    .build();
            policyAssignmentRepository.save(assignment);
            log.info("Policy {} applied to object {}", policyId, objectId);
        } else {
            log.info("Policy {} already assigned to object {}", policyId, objectId);
        }
        changeLogService.logChange(objectId, ChangeType.SECURITY, "Policy applied: " + policyId + " to object: " + objectId);
    }

    @Override
    public void removePolicyFromObject(String objectId, String policyId) {
        policyAssignmentRepository.deleteByObjectIdAndPolicyId(objectId, policyId);
        log.info("Policy {} removed from object {}", policyId, objectId);
        changeLogService.logChange(objectId, ChangeType.SECURITY, "Policy removed: " + policyId + " from object: " + objectId);

    }
}
