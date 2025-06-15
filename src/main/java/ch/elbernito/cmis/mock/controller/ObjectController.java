package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.ObjectDto;
import ch.elbernito.cmis.mock.service.ObjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objects")
@Tag(name = "Objects", description = "CMIS Object operations")
public class ObjectController {
    @Autowired
    private ObjectService objectService;

    @PostMapping
    @Operation(summary = "Create an object")
    public ResponseEntity<ObjectDto> create(@RequestBody ObjectDto dto) {
        return ResponseEntity.status(201).body(objectService.createObject(dto));
    }

    @GetMapping
    @Operation(summary = "List objects by type")
    public ResponseEntity<List<ObjectDto>> list(@RequestParam String type) {
        return ResponseEntity.ok(objectService.listObjects(type));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get object by ID")
    public ResponseEntity<ObjectDto> get(@PathVariable String id) {
        return ResponseEntity.ok(objectService.getObject(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete object")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        objectService.deleteObject(id);
        return ResponseEntity.noContent().build();
    }
}
