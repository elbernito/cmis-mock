package ch.elbernito.cmis.mock.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @GetMapping({"/", "/index"})
    public String index() {
        log.info("Accessed CMIS mock index page");
        return "index";
    }
}
