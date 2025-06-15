package ch.elbernito.cmis.mock.web;

import ch.elbernito.cmis.mock.dto.FolderDto;
import ch.elbernito.cmis.mock.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web controller for managing folders via Thymeleaf views.
 */
@Controller
@RequestMapping("/folders")
public class FolderWebController {

    @Autowired
    private FolderService folderService;

    @GetMapping
    public String list(Model m) {
        m.addAttribute("folders", folderService.listFolders(0,50).getContent());
        return "folders";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model m) {
        FolderDto dto = folderService.getFolder(id);
        m.addAttribute("editId", dto.getId());
        m.addAttribute("editName", dto.getName());
        m.addAttribute("editParent", dto.getParentId());
        m.addAttribute("folders", folderService.listFolders(0,50).getContent());
        return "folders";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute FolderDto dto) {
        folderService.updateFolder(id, dto);
        return "redirect:/folders";
    }

    @PostMapping
    public String create(@ModelAttribute FolderDto dto) {
        folderService.createFolder(dto);
        return "redirect:/folders";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        folderService.deleteFolder(id);
        return "redirect:/folders";
    }
}
