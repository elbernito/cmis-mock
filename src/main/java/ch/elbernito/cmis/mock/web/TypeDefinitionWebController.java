package ch.elbernito.cmis.mock.web;

import ch.elbernito.cmis.mock.dto.TypeDefinitionDto;
import ch.elbernito.cmis.mock.service.TypeDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web controller for managing type definitions via Thymeleaf views.
 */
@Controller
@RequestMapping("/typedefinitions")
public class TypeDefinitionWebController {

    @Autowired
    private TypeDefinitionService typeDefinitionService;

    @GetMapping
    public String list(Model m) {
        m.addAttribute("typedefinitions", typeDefinitionService.listTypeDefinitions());
        return "typedefinitions";  
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model m) {
        TypeDefinitionDto dto = typeDefinitionService.getTypeDefinition(id);
        m.addAttribute("editId", dto.getId());
        m.addAttribute("editDisplay", dto.getDisplayName());
        m.addAttribute("typedefinitions", typeDefinitionService.listTypeDefinitions());
        return "typedefinitions";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute TypeDefinitionDto dto) {
        typeDefinitionService.updateTypeDefinition(id, dto);
        return "redirect:/typedefinitions";
    }

    @PostMapping
    public String create(@ModelAttribute TypeDefinitionDto dto) {
        typeDefinitionService.createTypeDefinition(dto);
        return "redirect:/typedefinitions";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        typeDefinitionService.deleteTypeDefinition(id);
        return "redirect:/typedefinitions";
    }
}
