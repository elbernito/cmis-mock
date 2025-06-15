package ch.elbernito.cmis.mock.web;

import ch.elbernito.cmis.mock.dto.AclDto;
import ch.elbernito.cmis.mock.service.AclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web controller for managing ACLs via Thymeleaf views.
 */
@Controller
@RequestMapping("/acls")
public class AclWebController {

    @Autowired
    private AclService aclService;

    @GetMapping
    public String list(Model m) {
        // TODO aus modell die id ziehen und abfragen.
        m.addAttribute("acls", aclService.listAcls("1"));
        return "acls";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model m) {
        AclDto dto = aclService.getAcl(id);
        m.addAttribute("editId", dto.getId());
        m.addAttribute("editPermission", dto.getPermission());
        m.addAttribute("acls", aclService.listAcls(id));
        return "acls";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute AclDto dto) {
        aclService.updateAcl(id, dto);
        return "redirect:/acls";
    }

    @PostMapping
    public String create(@ModelAttribute AclDto dto) {
        aclService.createAcl(dto);
        return "redirect:/acls";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        aclService.deleteAcl(id);
        return "redirect:/acls";
    }
}
