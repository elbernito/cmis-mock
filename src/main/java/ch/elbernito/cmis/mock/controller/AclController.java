package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.AclDto;
import ch.elbernito.cmis.mock.service.AclService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for CMIS ACL operations.
 */
@RestController
@RequestMapping("/api/crud/objects/{objectId}/acl")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "ACL", description = "CMIS Access Control List management API")
public class AclController {

    private final AclService aclService;

    @Operation(summary = "Get ACL for object", responses = {@ApiResponse(responseCode = "200", description = "ACL listed")})
    @GetMapping
    public ResponseEntity<List<AclDto>> getAcl(@PathVariable String objectId) {
        log.info("GET /api/crud/crud/objects/{}/acl", objectId);
        return ResponseEntity.ok(aclService.getAclForObject(objectId));
    }

    @Operation(summary = "Set ACL for object", responses = {@ApiResponse(responseCode = "200", description = "ACL set")})
    @PutMapping
    public ResponseEntity<AclDto> setAcl(@PathVariable String objectId, @RequestBody AclDto aclDto) {
        log.info("PUT /api/crud/objects/{}/acl", objectId);
        return ResponseEntity.ok(aclService.setAclForObject(objectId, aclDto));
    }

    @Operation(summary = "Update single ACL by aclId", responses = {@ApiResponse(responseCode = "200", description = "ACL updated")})
    @PutMapping("/{aclId}")
    public ResponseEntity<AclDto> updateAcl(@PathVariable String objectId, @PathVariable String aclId, @RequestBody AclDto aclDto) {
        log.info("PUT /api/crud/objects/{}/acl/{}", objectId, aclId);
        return ResponseEntity.ok(aclService.updateAcl(aclId, aclDto));
    }

    @Operation(summary = "Delete ACL by aclId", responses = {@ApiResponse(responseCode = "204", description = "ACL deleted")})
    @DeleteMapping("/{aclId}")
    public ResponseEntity<Void> deleteAcl(@PathVariable String objectId, @PathVariable String aclId) {
        log.info("DELETE /api/crud/objects/{}/acl/{}", objectId, aclId);
        aclService.deleteAcl(aclId);
        return ResponseEntity.noContent().build();
    }
}
