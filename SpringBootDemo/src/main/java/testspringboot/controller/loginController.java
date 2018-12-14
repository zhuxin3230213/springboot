package testspringboot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.HashMap;

@Controller
@RequestMapping("/login")
public class loginController {

    @RequestMapping("/test")
    public String login(Model model) {
        model.addAttribute("hello", "欢迎进入HTML页面");
        return "login";
    }
}
