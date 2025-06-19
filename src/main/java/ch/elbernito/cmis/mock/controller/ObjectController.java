package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.ObjectDto;
import ch.elbernito.cmis.mock.service.ObjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for generic CMIS Objects.
 */
@RestController
@RequestMapping("/api/objects")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Object", description = "Generic CMIS Object operations")
public class ObjectController {

    private final ObjectService cmisObjectService;

    /**
     * Get generic object by ID.
     */
    @Operation(summary = "Get object by objectId",
            responses = {@ApiResponse(responseCode = "200", description = "CMIS Object found")})
    @GetMapping("/{objectId}")
    public ResponseEntity<ObjectDto> getObject(@PathVariable String objectId) {
        log.info("GET /api/objects/{}", objectId);
        return ResponseEntity.ok(cmisObjectService.getObjectById(objectId));
    }

    /**
     * Get object by path (query param ?path=).
     */
    @Operation(summary = "Get object by path",
            responses = {@ApiResponse(responseCode = "200", description = "CMIS Object found")})
    @GetMapping("/by-path")
    public ResponseEntity<ObjectDto> getObjectByPath(@RequestParam String path) {
        log.info("GET /api/objects/by-path?path={}", path);
        return ResponseEntity.ok(cmisObjectService.getObjectByPath(path));
    }

    /**
     * Move object to a new folder.
     */
    @Operation(summary = "Move object",
            responses = {@ApiResponse(responseCode = "200", description = "CMIS Object moved")})
    @PostMapping("/{objectId}/move")
    public ResponseEntity<ObjectDto> moveObject(@PathVariable String objectId, @RequestBody Map<String, String> body) {
        String targetFolderId = body.get("targetFolderId");
        log.info("POST /api/objects/{}/move to {}", objectId, targetFolderId);
        return ResponseEntity.ok(cmisObjectService.moveObject(objectId, targetFolderId));
    }

    /**
     * Copy object to a new folder.
     */
    @Operation(summary = "Copy object",
            responses = {@ApiResponse(responseCode = "200", description = "CMIS Object copied")})
    @PostMapping("/{objectId}/copy")
    public ResponseEntity<ObjectDto> copyObject(@PathVariable String objectId, @RequestBody Map<String, String> body) {
        String targetFolderId = body.get("targetFolderId");
        log.info("POST /api/objects/{}/copy to {}", objectId, targetFolderId);
        return ResponseEntity.ok(cmisObjectService.copyObject(objectId, targetFolderId));
    }

    /**
     * Get allowable actions for an object.
     */
    @Operation(summary = "Get allowable actions for object",
            responses = {@ApiResponse(responseCode = "200", description = "Allowable actions list")})
    @GetMapping("/{objectId}/allowableActions")
    public ResponseEntity<List<String>> getAllowableActions(@PathVariable String objectId) {
        log.info("GET /api/objects/{}/allowableActions", objectId);
        return ResponseEntity.ok(cmisObjectService.getAllowableActions(objectId));
    }

    /**
     * Get relationships for an object.
     */
    @Operation(summary = "Get relationships for object",
            responses = {@ApiResponse(responseCode = "200", description = "List of relationships")})
    @GetMapping("/{objectId}/relationships")
    public ResponseEntity<List<ObjectDto>> getRelationships(@PathVariable String objectId) {
        log.info("GET /api/objects/{}/relationships", objectId);
        return ResponseEntity.ok(cmisObjectService.getRelationships(objectId));
    }
}
