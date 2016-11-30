package wenjing.xdtic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author admin
 */
@Controller
public class HomeController {

    @RequestMapping({"/", "index", "home", "test", "login"})
    public String index() {
        return "/page/user/login";
    }

    @RequestMapping("register")
    public String login() {
        return "register";
    }
}
