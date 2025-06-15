package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.PolicyDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PolicyService {
    PolicyDto createPolicy(PolicyDto dto);
    Page<PolicyDto> listPolicies(int page, int size);
    PolicyDto getPolicy(String id);
    PolicyDto updatePolicy(String id, PolicyDto dto);
    void deletePolicy(String id);
}
