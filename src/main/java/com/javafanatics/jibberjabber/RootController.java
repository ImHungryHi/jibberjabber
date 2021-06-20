package com.javafanatics.jibberjabber;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class RootController {
    @GetMapping("/")
    public String handleRedirect(Principal principal) {
        if (principal == null) {
            return "redirect:login";
        }

        return "redirect:home";
    }
}
