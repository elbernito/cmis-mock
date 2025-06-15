package ch.elbernito.cmis.mock.web;

import ch.elbernito.cmis.mock.dto.DocumentDto;
import ch.elbernito.cmis.mock.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Web controller for managing documents via Thymeleaf views.
 */
@Controller
@RequestMapping("/documents")
public class DocumentWebController {

    @Autowired
    private DocumentService documentService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("documents", documentService.listDocuments(0, 50).getContent());
        return "documents";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        DocumentDto dto = documentService.getDocument(id);
        model.addAttribute("editId", dto.getId());
        model.addAttribute("documents", documentService.listDocuments(0,50).getContent());
        return "documents";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        documentService.updateDocument(id, file);
        return "redirect:/documents";
    }

    @PostMapping
    public String create(@RequestParam("file") MultipartFile file) {
        documentService.createDocument(file);
        return "redirect:/documents";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        documentService.deleteDocument(id);
        return "redirect:/documents";
    }
}
