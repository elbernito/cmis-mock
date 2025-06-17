package ch.elbernito.cmis.mock.controller;

import ch.elbernito.cmis.mock.dto.StatisticsDto;
import ch.elbernito.cmis.mock.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/current")
    @Operation(summary = "Get current repository statistics")
    public ResponseEntity<StatisticsDto> getCurrentStatistics() {
        return ResponseEntity.ok(statisticsService.getCurrentStatistics());
    }
}
