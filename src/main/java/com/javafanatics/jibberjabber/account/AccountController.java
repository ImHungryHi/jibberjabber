package com.javafanatics.jibberjabber.account;
import com.javafanatics.jibberjabber.Tools;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        List<User> users;

        if (principal != null) {
            String handle = principal.getName();
            User activeUser = userService.getByHandle(handle);
            model.addAttribute("handle", handle);
            model.addAttribute("activeUser", activeUser);
            users = userService.getAllSortFollows(activeUser);
        }
        else {
            users = userService.getAll();
        }

        model.addAttribute("tools", new Tools());
        model.addAttribute("users", users);

        return "account/list";
    }

    //<!--th:object="${user}"-->
    @PostMapping("/user/unfollow")
    public String doUnfollow(@ModelAttribute User user, Authentication authentication) {
        String activeUserName = authentication.getName();
        User activeUser = userService.getByHandle(activeUserName);
        User actualUser = userService.getByHandle(user.getHandle());
        userService.removeFollow(activeUser, actualUser);
        return "redirect:/users";
    }

    @PostMapping("/user/follow")
    public String doFollow(@ModelAttribute User user, Authentication authentication) {
        String activeUserName = authentication.getName();
        User activeUser = userService.getByHandle(activeUserName);
        User actualUser = userService.getByHandle(user.getHandle());
        userService.addFollow(activeUser, actualUser);
        return "redirect:/users";
    }
}
