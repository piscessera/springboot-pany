package com.pany.springboot.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger("HomeController");

    @RequestMapping("/")
    public String home() {
        log.info("Access home");
        return "index";
    }
}
