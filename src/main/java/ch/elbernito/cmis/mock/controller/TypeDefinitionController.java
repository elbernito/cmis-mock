package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.TypeDefinitionDto;
import ch.elbernito.cmis.mock.service.TypeDefinitionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/type-definitions")
@RequiredArgsConstructor
public class TypeDefinitionController {

    private static final Logger logger = LoggerFactory.getLogger(TypeDefinitionController.class);

    private final TypeDefinitionService typeDefinitionService;

    @PostMapping
    public ResponseEntity<TypeDefinitionDto> create(@RequestBody TypeDefinitionDto dto) {
        logger.info("REST request to create TypeDefinition: {}", dto);
        return ResponseEntity.ok(typeDefinitionService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeDefinitionDto> get(@PathVariable Long id) {
        logger.info("REST request to get TypeDefinition: {}", id);
        return ResponseEntity.ok(typeDefinitionService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<TypeDefinitionDto>> getAll() {
        logger.info("REST request to get all TypeDefinitions");
        return ResponseEntity.ok(typeDefinitionService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeDefinitionDto> update(@PathVariable Long id, @RequestBody TypeDefinitionDto dto) {
        logger.info("REST request to update TypeDefinition: {}", id);
        return ResponseEntity.ok(typeDefinitionService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("REST request to delete TypeDefinition: {}", id);
        typeDefinitionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
