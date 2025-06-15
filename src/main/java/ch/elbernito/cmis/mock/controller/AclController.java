package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.AclDto;
import ch.elbernito.cmis.mock.service.AclService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acls")
@Tag(name = "ACLs", description = "CMIS ACL operations")
public class AclController {
    @Autowired
    private AclService aclService;

    @PostMapping
    @Operation(summary = "Create ACL entry")
    public ResponseEntity<AclDto> create(@RequestBody AclDto dto) {
        return ResponseEntity.status(201).body(aclService.createAcl(dto));
    }

    @GetMapping
    @Operation(summary = "List ACL entries for an object")
    public ResponseEntity<List<AclDto>> list(@RequestParam String objectId) {
        return ResponseEntity.ok(aclService.listAcls(objectId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ACL by ID")
    public ResponseEntity<AclDto> get(@PathVariable String id) {
        return ResponseEntity.ok(aclService.getAcl(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete ACL")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        aclService.deleteAcl(id);
        return ResponseEntity.noContent().build();
    }
}
