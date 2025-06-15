package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.DiscoveryDto;
import ch.elbernito.cmis.mock.service.DiscoveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discovery")
@Tag(name = "Discovery", description = "CMIS Discovery operations")
public class DiscoveryController {
    @Autowired
    private DiscoveryService discoveryService;

    @PostMapping
    @Operation(summary = "Create a discovery query")
    public ResponseEntity<DiscoveryDto> create(@RequestBody DiscoveryDto dto) {
        return ResponseEntity.status(201).body(discoveryService.createDiscovery(dto));
    }

    @GetMapping
    @Operation(summary = "List discovery queries")
    public ResponseEntity<List<DiscoveryDto>> list() {
        return ResponseEntity.ok(discoveryService.listDiscoveries());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get discovery by ID")
    public ResponseEntity<DiscoveryDto> get(@PathVariable String id) {
        return ResponseEntity.ok(discoveryService.getDiscovery(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete discovery")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        discoveryService.deleteDiscovery(id);
        return ResponseEntity.noContent().build();
    }
}
