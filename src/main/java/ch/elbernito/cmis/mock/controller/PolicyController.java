package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.PolicyDto;
import ch.elbernito.cmis.mock.service.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for CMIS Policy operations.
 */
@RestController
@RequestMapping("/api/crud/policies")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Policy", description = "CMIS Policy management API")
public class PolicyController {

    private final PolicyService policyService;

    @Operation(summary = "Create policy", responses = {@ApiResponse(responseCode = "200", description = "Policy created")})
    @PostMapping
    public ResponseEntity<PolicyDto> createPolicy(@RequestBody PolicyDto dto) {
        log.info("POST /api/crud/policies");
        return ResponseEntity.ok(policyService.createPolicy(dto));
    }

    @Operation(summary = "Get policy by policyId", responses = {@ApiResponse(responseCode = "200", description = "Policy found")})
    @GetMapping("/{policyId}")
    public ResponseEntity<PolicyDto> getPolicy(@PathVariable String policyId) {
        log.info("GET /api/crud/policies/{}", policyId);
        return ResponseEntity.ok(policyService.getPolicy(policyId));
    }

    @Operation(summary = "Delete policy by policyId", responses = {@ApiResponse(responseCode = "204", description = "Policy deleted")})
    @DeleteMapping("/{policyId}")
    public ResponseEntity<Void> deletePolicy(@PathVariable String policyId) {
        log.info("DELETE /api/crud/policies/{}", policyId);
        policyService.deletePolicy(policyId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all policies", responses = {@ApiResponse(responseCode = "200", description = "Policies listed")})
    @GetMapping
    public ResponseEntity<List<PolicyDto>> getAllPolicies() {
        log.info("GET /api/crud/policies");
        return ResponseEntity.ok(policyService.getAllPolicies());
    }

    @Operation(summary = "Apply policy to object", responses = {@ApiResponse(responseCode = "204", description = "Policy applied")})
    @PostMapping("/apply")
    public ResponseEntity<Void> applyPolicyToObject(@RequestParam String objectId, @RequestParam String policyId) {
        log.info("POST /api/crud/policies/apply object={} policy={}", objectId, policyId);
        policyService.applyPolicyToObject(objectId, policyId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove policy from object", responses = {@ApiResponse(responseCode = "204", description = "Policy removed")})
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removePolicyFromObject(@RequestParam String objectId, @RequestParam String policyId) {
        log.info("DELETE /api/crud/policies/remove object={} policy={}", objectId, policyId);
        policyService.removePolicyFromObject(objectId, policyId);
        return ResponseEntity.noContent().build();
    }
}
