package com.javafanatics.jibberjabber.account;
import com.javafanatics.jibberjabber.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.List;

@Controller
public class AccountController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUsers(Model model, Principal principal) {
        if (principal != null) {
            String handle = principal.getName();
            model.addAttribute("handle", handle);
        }

        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("tools", new Tools());

        return "account/list";
    }
}
