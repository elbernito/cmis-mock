package ch.elbernito.cmis.mock.web;

import ch.elbernito.cmis.mock.dto.PolicyDto;
import ch.elbernito.cmis.mock.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web controller for managing policies via Thymeleaf views.
 */
@Controller
@RequestMapping("/policies")
public class PolicyWebController {

    @Autowired
    private PolicyService policyService;

    @GetMapping
    public String list(Model m) {
        m.addAttribute("policies", policyService.listPolicies(0,50).getContent());
        return "policies";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model m) {
        PolicyDto dto = policyService.getPolicy(id);
        m.addAttribute("editId", dto.getId());
        m.addAttribute("editDescription", dto.getDescription());
        m.addAttribute("policies", policyService.listPolicies(0,50).getContent());
        return "policies";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute PolicyDto dto) {
        policyService.updatePolicy(id, dto);
        return "redirect:/policies";
    }

    @PostMapping
    public String create(@ModelAttribute PolicyDto dto) {
        policyService.createPolicy(dto);
        return "redirect:/policies";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        policyService.deletePolicy(id);
        return "redirect:/policies";
    }
}
