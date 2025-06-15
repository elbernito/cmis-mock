package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.PolicyDto;
import ch.elbernito.cmis.mock.service.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
@Tag(name = "Policies", description = "CMIS Policy operations")
public class PolicyController {
    @Autowired
    private PolicyService policyService;

    @PostMapping
    @Operation(summary = "Create a new policy")
    public ResponseEntity<PolicyDto> create(@RequestBody PolicyDto dto) {
        return ResponseEntity.status(201).body(policyService.createPolicy(dto));
    }

    @GetMapping
    @Operation(summary = "List policies with paging")
    public ResponseEntity<List<PolicyDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(policyService.listPolicies(page, size).getContent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get policy by ID")
    public ResponseEntity<PolicyDto> get(@PathVariable String id) {
        return ResponseEntity.ok(policyService.getPolicy(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update policy")
    public ResponseEntity<PolicyDto> update(
            @PathVariable String id,
            @RequestBody PolicyDto dto) {
        return ResponseEntity.ok(policyService.updatePolicy(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete policy")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        policyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}
