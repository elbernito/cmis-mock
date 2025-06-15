package ch.elbernito.cmis.mock.service;

import ch.elbernito.cmis.mock.dto.StatisticsDto;
import java.util.List;

public interface StatisticsService {
    StatisticsDto createStatistic(StatisticsDto dto);
    List<StatisticsDto> listStatistics();
    StatisticsDto getStatistic(String id);
    StatisticsDto updateStatistic(String id, StatisticsDto dto);
    void deleteStatistic(String id);
}
