package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.PolicyDto;

import java.util.List;

public interface PolicyService {
    PolicyDto createPolicy(PolicyDto dto);
    PolicyDto updatePolicy(Long id, PolicyDto dto);
    void deletePolicy(Long id);
    PolicyDto getPolicyById(Long id);
    List<PolicyDto> getAllPolicies();
    PolicyDto getPolicyByPolicyId(String policyId);
}
