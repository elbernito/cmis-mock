package ch.elbernito.cmis.mock.web;

import ch.elbernito.cmis.mock.dto.ObjectDto;
import ch.elbernito.cmis.mock.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web controller for managing objects via Thymeleaf views.
 */
@Controller
@RequestMapping("/objects")
public class ObjectWebController {

    @Autowired
    private ObjectService objectService;

    @GetMapping
    public String list(Model m) {
        // TODO aus modell die id ziehen und abfragen.
        m.addAttribute("objects", objectService.listObjects("1"));
        return "objects";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model m) {
        ObjectDto dto = objectService.getObject(id);
        m.addAttribute("editId", dto.getId());
        m.addAttribute("editName", dto.getName());
        m.addAttribute("objects", objectService.listObjects(id));
        return "objects";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute ObjectDto dto) {
        objectService.updateObject(id, dto);
        return "redirect:/objects";
    }

    @PostMapping
    public String create(@ModelAttribute ObjectDto dto) {
        objectService.createObject(dto);
        return "redirect:/objects";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        objectService.deleteObject(id);
        return "redirect:/objects";
    }
}
