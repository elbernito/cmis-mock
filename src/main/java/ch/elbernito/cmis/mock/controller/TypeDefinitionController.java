package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.TypeDefinitionDto;
import ch.elbernito.cmis.mock.service.TypeDefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for CMIS TypeDefinition operations.
 */
@RestController
@RequestMapping("/api/types")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "TypeDefinition", description = "CMIS TypeDefinition management API")
public class TypeDefinitionController {

    private final TypeDefinitionService typeDefinitionService;

    @Operation(summary = "Get all type definitions", responses = {@ApiResponse(responseCode = "200", description = "Types listed")})
    @GetMapping
    public ResponseEntity<List<TypeDefinitionDto>> getAllTypes() {
        log.info("GET /api/types");
        return ResponseEntity.ok(typeDefinitionService.getAllTypeDefinitions());
    }

    @Operation(summary = "Get type definition by typeId", responses = {@ApiResponse(responseCode = "200", description = "Type found")})
    @GetMapping("/{typeId}")
    public ResponseEntity<TypeDefinitionDto> getType(@PathVariable String typeId) {
        log.info("GET /api/types/{}", typeId);
        return ResponseEntity.ok(typeDefinitionService.getTypeDefinition(typeId));
    }

    @Operation(summary = "Create type definition", responses = {@ApiResponse(responseCode = "200", description = "Type created")})
    @PostMapping
    public ResponseEntity<TypeDefinitionDto> createType(@RequestBody TypeDefinitionDto dto) {
        log.info("POST /api/types");
        return ResponseEntity.ok(typeDefinitionService.createTypeDefinition(dto));
    }
}
