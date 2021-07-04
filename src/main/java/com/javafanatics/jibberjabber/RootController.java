package com.javafanatics.jibberjabber;
import com.javafanatics.jibberjabber.account.UserService;
import com.javafanatics.jibberjabber.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.List;

@Controller
public class RootController {
    private UserService userService;
    private JibberService jibberService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTweetService(JibberService jibberService) {
        this.jibberService = jibberService;
    }

    @GetMapping("/")
    public String handleRedirect(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:login";
        }

        String handle = principal.getName();
        List<Jibber> jibbers = jibberService.getJibbersByUserHandle(handle);

        model.addAttribute("handle", handle);
        model.addAttribute("jibbers", jibbers);
        model.addAttribute("tools", new Tools());

        return "home";
    }
}
