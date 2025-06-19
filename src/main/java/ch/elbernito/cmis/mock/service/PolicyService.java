package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.PolicyDto;

import java.util.List;

/**
 * Service interface for Policy management.
 */
public interface PolicyService {

    PolicyDto createPolicy(PolicyDto policyDto);

    PolicyDto getPolicy(String policyId);

    void deletePolicy(String policyId);

    List<PolicyDto> getAllPolicies();

    void applyPolicyToObject(String objectId, String policyId);

    void removePolicyFromObject(String objectId, String policyId);
}
