package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.TypeDefinitionDto;
import ch.elbernito.cmis.mock.service.TypeDefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/typedefinitions")
@Tag(name = "TypeDefinitions", description = "CMIS Type Definition operations")
public class TypeDefinitionController {
    @Autowired
    private TypeDefinitionService typeService;

    @PostMapping
    @Operation(summary = "Create a type definition")
    public ResponseEntity<TypeDefinitionDto> create(@RequestBody TypeDefinitionDto dto) {
        return ResponseEntity.status(201).body(typeService.createTypeDefinition(dto));
    }

    @GetMapping
    @Operation(summary = "List all type definitions")
    public ResponseEntity<List<TypeDefinitionDto>> list() {
        return ResponseEntity.ok(typeService.listTypeDefinitions());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get type definition by ID")
    public ResponseEntity<TypeDefinitionDto> get(@PathVariable String id) {
        return ResponseEntity.ok(typeService.getTypeDefinition(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete type definition")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        typeService.deleteTypeDefinition(id);
        return ResponseEntity.noContent().build();
    }
}
