package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.PolicyDto;
import ch.elbernito.cmis.mock.service.PolicyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policy")
@RequiredArgsConstructor
@Tag(name = "Policy", description = "CMIS Policy Management")
public class PolicyController {

    private static final Logger logger = LoggerFactory.getLogger(PolicyController.class);
    private final PolicyService policyService;

    @PostMapping
    public ResponseEntity<PolicyDto> createPolicy(@RequestBody PolicyDto dto) {
        logger.info("REST request to create policy: {}", dto);
        return ResponseEntity.ok(policyService.createPolicy(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PolicyDto> updatePolicy(@PathVariable Long id, @RequestBody PolicyDto dto) {
        logger.info("REST request to update policy {}: {}", id, dto);
        return ResponseEntity.ok(policyService.updatePolicy(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        logger.info("REST request to delete policy: {}", id);
        policyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyDto> getPolicy(@PathVariable Long id) {
        logger.info("REST request to get policy: {}", id);
        return ResponseEntity.ok(policyService.getPolicyById(id));
    }

    @GetMapping
    public ResponseEntity<List<PolicyDto>> getAllPolicies() {
        logger.info("REST request to get all policies");
        return ResponseEntity.ok(policyService.getAllPolicies());
    }

    @GetMapping("/byPolicyId/{policyId}")
    public ResponseEntity<PolicyDto> getPolicyByPolicyId(@PathVariable String policyId) {
        logger.info("REST request to get policy by policyId: {}", policyId);
        return ResponseEntity.ok(policyService.getPolicyByPolicyId(policyId));
    }
}
