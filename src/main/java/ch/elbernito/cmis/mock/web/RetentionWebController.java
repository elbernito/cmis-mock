package ch.elbernito.cmis.mock.web;

import ch.elbernito.cmis.mock.dto.RetentionDto;
import ch.elbernito.cmis.mock.service.RetentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web controller for managing retention entries via Thymeleaf views.
 */
@Controller
@RequestMapping("/retention")
public class RetentionWebController {

    @Autowired
    private RetentionService retentionService;

    @GetMapping
    public String list(Model m) {
        m.addAttribute("retention", retentionService.listRetentions(0,50).getContent());
        return "retention";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model m) {
        RetentionDto dto = retentionService.getRetention(id);
        m.addAttribute("editId", dto.getId());
        m.addAttribute("editPolicy", dto.getRetentionPolicyName());
        m.addAttribute("retention", retentionService.listRetentions(0,50).getContent());
        return "retention";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute RetentionDto dto) {
        retentionService.updateRetention(id, dto);
        return "redirect:/retention";
    }

    @PostMapping
    public String create(@ModelAttribute RetentionDto dto) {
        retentionService.createRetention(dto);
        return "redirect:/retention";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        retentionService.deleteRetention(id);
        return "redirect:/retention";
    }
}
