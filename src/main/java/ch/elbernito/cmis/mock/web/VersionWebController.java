package ch.elbernito.cmis.mock.web;

import ch.elbernito.cmis.mock.dto.VersionDto;
import ch.elbernito.cmis.mock.service.VersioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web controller for managing versions via Thymeleaf views.
 */
@Controller
@RequestMapping("/versions")
public class VersionWebController {

    @Autowired
    private VersioningService versioningService;

    @GetMapping
    public String list(Model m) {
        m.addAttribute("versions", versioningService.listVersions(0,50).getContent());
        return "versions";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model m) {
        VersionDto dto = versioningService.getVersion(id);
        m.addAttribute("editId", dto.getId());
        m.addAttribute("editLabel", dto.getVersionLabel());
        m.addAttribute("versions", versioningService.listVersions(0,50).getContent());
        return "versions";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute VersionDto dto) {
        versioningService.updateVersion(id, dto);
        return "redirect:/versions";
    }

    @PostMapping
    public String create(@ModelAttribute VersionDto dto) {
        versioningService.createVersion(dto);
        return "redirect:/versions";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        versioningService.deleteVersion(id);
        return "redirect:/versions";
    }
}
