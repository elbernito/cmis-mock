package ch.elbernito.cmis.mock.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the application home page.
 */
@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String index() {
        // zeigt nun index.html
        return "index";
    }
}
