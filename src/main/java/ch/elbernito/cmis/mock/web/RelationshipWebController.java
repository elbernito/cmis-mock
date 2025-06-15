package ch.elbernito.cmis.mock.web;

import ch.elbernito.cmis.mock.dto.RelationshipDto;
import ch.elbernito.cmis.mock.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web controller for managing relationships via Thymeleaf views.
 */
@Controller
@RequestMapping("/relationships")
public class RelationshipWebController {

    @Autowired
    private RelationshipService relationshipService;

    @GetMapping
    public String list(Model m) {
        // TODO aus modell die id ziehen und abfragen.
        m.addAttribute("relationships", relationshipService.listRelationships("1"));
        return "relationships";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model m) {
        RelationshipDto dto = relationshipService.getRelationship(id);
        m.addAttribute("editId", dto.getId());
        m.addAttribute("editRelation", dto.getRelationType());
        m.addAttribute("relationships", relationshipService.listRelationships(id));
        return "relationships";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute RelationshipDto dto) {
        relationshipService.updateRelationship(id, dto);
        return "redirect:/relationships";
    }

    @PostMapping
    public String create(@ModelAttribute RelationshipDto dto) {
        relationshipService.createRelationship(dto);
        return "redirect:/relationships";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        relationshipService.deleteRelationship(id);
        return "redirect:/relationships";
    }
}
