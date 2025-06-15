package ch.elbernito.cmis.mock.web;

import ch.elbernito.cmis.mock.dto.StatisticsDto;
import ch.elbernito.cmis.mock.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web controller for managing statistics via Thymeleaf views.
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsWebController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping
    public String list(Model m) {
        m.addAttribute("statistics", statisticsService.listStatistics());
        return "statistics";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model m) {
        StatisticsDto dto = statisticsService.getStatistic(id);
        m.addAttribute("editId", dto.getId());
        m.addAttribute("editValue", dto.getMetricValue());
        m.addAttribute("statistics", statisticsService.listStatistics());
        return "statistics";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute StatisticsDto dto) {
        statisticsService.updateStatistic(id, dto);
        return "redirect:/statistics";
    }

    @PostMapping
    public String create(@ModelAttribute StatisticsDto dto) {
        statisticsService.createStatistic(dto);
        return "redirect:/statistics";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        statisticsService.deleteStatistic(id);
        return "redirect:/statistics";
    }
}
