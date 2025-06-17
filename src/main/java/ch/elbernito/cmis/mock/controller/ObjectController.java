package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.ObjectDto;
import ch.elbernito.cmis.mock.service.ObjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for CMIS Object.
 */
@RestController
@RequestMapping("/api/objects")
@Tag(name = "Object", description = "CMIS Object API")
public class ObjectController {

    private static final Logger log = LoggerFactory.getLogger(ObjectController.class);

    private final ObjectService objectService;

    public ObjectController(ObjectService objectService) {
        this.objectService = objectService;
    }

    @PostMapping
    public ResponseEntity<ObjectDto> createObject(@RequestBody ObjectDto dto) {
        log.info("REST request to create CMIS Object: {}", dto);
        return ResponseEntity.ok(objectService.createObject(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectDto> getObject(@PathVariable Long id) {
        log.info("REST request to get CMIS Object by id: {}", id);
        return ResponseEntity.ok(objectService.getObjectById(id));
    }

    @GetMapping
    public ResponseEntity<List<ObjectDto>> getAllObjects() {
        log.info("REST request to get all CMIS Objects");
        return ResponseEntity.ok(objectService.getAllObjects());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectDto> updateObject(@PathVariable Long id, @RequestBody ObjectDto dto) {
        log.info("REST request to update CMIS Object {}: {}", id, dto);
        return ResponseEntity.ok(objectService.updateObject(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObject(@PathVariable Long id) {
        log.info("REST request to delete CMIS Object: {}", id);
        objectService.deleteObject(id);
        return ResponseEntity.noContent().build();
    }
}
