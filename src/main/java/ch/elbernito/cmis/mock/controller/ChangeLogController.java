package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.ChangeLogDto;
import ch.elbernito.cmis.mock.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/changelog")
@RequiredArgsConstructor
public class ChangeLogController {
    private final Logger log = LoggerFactory.getLogger(ChangeLogController.class);
    private final ChangeLogService changeLogService;

    @PostMapping
    public ChangeLogDto createChangeLog(@RequestBody ChangeLogDto dto) {
        log.info("REST request to create changelog: {}", dto);
        return changeLogService.createChangeLog(dto);
    }

    @GetMapping
    public List<ChangeLogDto> getAllChangeLogs() {
        log.info("REST request to get all changelogs");
        return changeLogService.getAllChangeLogs();
    }

    @GetMapping("/object/{objectId}")
    public List<ChangeLogDto> getChangeLogsByObjectId(@PathVariable String objectId) {
        log.info("REST request to get changelogs by objectId: {}", objectId);
        return changeLogService.getChangeLogsByObjectId(objectId);
    }

    @DeleteMapping("/{id}")
    public void deleteChangeLog(@PathVariable Long id) {
        log.info("REST request to delete changelog: {}", id);
        changeLogService.deleteChangeLog(id);
    }
}
