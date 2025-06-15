package ch.elbernito.cmis.mock.web;

import ch.elbernito.cmis.mock.dto.DiscoveryDto;
import ch.elbernito.cmis.mock.service.DiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web controller for managing discovery queries via Thymeleaf views.
 */
@Controller
@RequestMapping("/discovery")
public class DiscoveryWebController {

    @Autowired
    private DiscoveryService discoveryService;

    @GetMapping
    public String list(Model m) {
        m.addAttribute("discovery", discoveryService.listDiscoveries());
        return "discovery";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model m) {
        DiscoveryDto dto = discoveryService.getDiscovery(id);
        m.addAttribute("editId", dto.getId());
        m.addAttribute("editDescription", dto.getDescription());
        m.addAttribute("discovery", discoveryService.listDiscoveries());
        return "discovery";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute DiscoveryDto dto) {
        discoveryService.updateDiscovery(id, dto);
        return "redirect:/discovery";
    }

    @PostMapping
    public String create(@ModelAttribute DiscoveryDto dto) {
        discoveryService.createDiscovery(dto);
        return "redirect:/discovery";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        discoveryService.deleteDiscovery(id);
        return "redirect:/discovery";
    }
}
