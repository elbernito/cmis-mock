package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.PolicyDto;
import ch.elbernito.cmis.mock.model.PolicyModel;
import ch.elbernito.cmis.mock.repository.PolicyRepository;
import ch.elbernito.cmis.mock.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {

    private static final Logger logger = LoggerFactory.getLogger(PolicyServiceImpl.class);
    private final PolicyRepository repository;

    @Override
    @Transactional
    public PolicyDto createPolicy(PolicyDto dto) {
        logger.info("Creating policy: {}", dto);
        PolicyModel model = toModel(dto);
        PolicyModel saved = repository.save(model);
        return toDto(saved);
    }

    @Override
    @Transactional
    public PolicyDto updatePolicy(Long id, PolicyDto dto) {
        logger.info("Updating policy {}: {}", id, dto);
        PolicyModel model = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Policy not found"));
        model.setName(dto.getName());
        model.setPolicyText(dto.getPolicyText());
        model.setCreatedBy(dto.getCreatedBy());
        model.setObjectId(dto.getObjectId());
        return toDto(repository.save(model));
    }

    @Override
    @Transactional
    public void deletePolicy(Long id) {
        logger.info("Deleting policy with id: {}", id);
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PolicyDto getPolicyById(Long id) {
        logger.info("Get policy by id: {}", id);
        return repository.findById(id)
                .map(PolicyServiceImpl::toDto)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PolicyDto> getAllPolicies() {
        logger.info("Get all policies");
        return repository.findAll().stream().map(PolicyServiceImpl::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PolicyDto getPolicyByPolicyId(String policyId) {
        logger.info("Get policy by policyId: {}", policyId);
        Optional<PolicyModel> model = repository.findByPolicyId(policyId);
        return model.map(PolicyServiceImpl::toDto).orElse(null);
    }

    public static PolicyModel toModel(PolicyDto dto) {
        PolicyModel model = new PolicyModel();
        model.setPolicyId(dto.getPolicyId());
        model.setName(dto.getName());
        model.setPolicyText(dto.getPolicyText());
        model.setCreatedBy(dto.getCreatedBy());
        model.setObjectId(dto.getObjectId());
        return model;
    }

    public static PolicyDto toDto(PolicyModel model) {
        PolicyDto dto = new PolicyDto();
        dto.setId(model.getId());
        dto.setPolicyId(model.getPolicyId());
        dto.setName(model.getName());
        dto.setPolicyText(model.getPolicyText());
        dto.setCreatedBy(model.getCreatedBy());
        dto.setObjectId(model.getObjectId());
        return dto;
    }
}
