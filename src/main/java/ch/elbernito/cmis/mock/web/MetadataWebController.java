package ch.elbernito.cmis.mock.web;

import ch.elbernito.cmis.mock.dto.MetadataDto;
import ch.elbernito.cmis.mock.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web controller for managing metadata via Thymeleaf views.
 */
@Controller
@RequestMapping("/metadata")
public class MetadataWebController {

    @Autowired
    private MetadataService metadataService;

    @GetMapping
    public String list(Model m) {
        m.addAttribute("metadata", metadataService.listMetadata(0,50).getContent());
        return "metadata";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model m) {
        MetadataDto dto = metadataService.getMetadata(id);
        m.addAttribute("editId", dto.getId());
        m.addAttribute("editProperty", dto.getPropertyName());
        m.addAttribute("editValue", dto.getPropertyValue());
        m.addAttribute("metadata", metadataService.listMetadata(0,50).getContent());
        return "metadata";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute MetadataDto dto) {
        metadataService.updateMetadata(id, dto);
        return "redirect:/metadata";
    }

    @PostMapping
    public String create(@ModelAttribute MetadataDto dto) {
        metadataService.createMetadata(dto);
        return "redirect:/metadata";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        metadataService.deleteMetadata(id);
        return "redirect:/metadata";
    }
}
