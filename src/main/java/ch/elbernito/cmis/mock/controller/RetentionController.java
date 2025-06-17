package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.RetentionDto;
import ch.elbernito.cmis.mock.service.RetentionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retentions")
@RequiredArgsConstructor
public class RetentionController {

    private static final Logger log = LoggerFactory.getLogger(RetentionController.class);

    private final RetentionService retentionService;

    @PostMapping
    @Operation(summary = "Create a retention")
    public RetentionDto create(@RequestBody RetentionDto dto) {
        log.info("REST request to create retention: {}", dto);
        return retentionService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a retention")
    public RetentionDto update(@PathVariable String id, @RequestBody RetentionDto dto) {
        log.info("REST request to update retention {}: {}", id, dto);
        return retentionService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a retention")
    public void delete(@PathVariable String id) {
        log.info("REST request to delete retention: {}", id);
        retentionService.delete(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get retention by ID")
    public RetentionDto get(@PathVariable String id) {
        log.info("REST request to get retention: {}", id);
        return retentionService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get all retentions")
    public List<RetentionDto> getAll() {
        log.info("REST request to get all retentions");
        return retentionService.getAll();
    }

    @GetMapping("/object/{objectId}")
    @Operation(summary = "Get all retentions for a specific object")
    public List<RetentionDto> getByObjectId(@PathVariable String objectId) {
        log.info("REST request to get retentions by objectId: {}", objectId);
        return retentionService.getByObjectId(objectId);
    }
}
