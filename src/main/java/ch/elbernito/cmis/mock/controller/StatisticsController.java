package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.StatisticsDto;
import ch.elbernito.cmis.mock.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@Tag(name = "Statistics", description = "CMIS Statistics operations")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @PostMapping
    @Operation(summary = "Create a statistic entry")
    public ResponseEntity<StatisticsDto> create(@RequestBody StatisticsDto dto) {
        return ResponseEntity.status(201).body(statisticsService.createStatistic(dto));
    }

    @GetMapping
    @Operation(summary = "List all statistic entries")
    public ResponseEntity<List<StatisticsDto>> list() {
        return ResponseEntity.ok(statisticsService.listStatistics());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get statistic entry by ID")
    public ResponseEntity<StatisticsDto> get(@PathVariable String id) {
        return ResponseEntity.ok(statisticsService.getStatistic(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete statistic entry by ID")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        statisticsService.deleteStatistic(id);
        return ResponseEntity.noContent().build();
    }
}
