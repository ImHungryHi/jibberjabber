package com.javafanatics.jibberjabber.message;
import com.javafanatics.jibberjabber.Tools;
import com.javafanatics.jibberjabber.account.User;
import com.javafanatics.jibberjabber.account.UserService;
import com.javafanatics.jibberjabber.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class JibberController {
    private UserService userService;
    private JibberService jibberService;
    private NotificationService notificationService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJibberService(JibberService jibberService) {
        this.jibberService = jibberService;
    }

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/")
    public String showHome(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:login";
        }

        String handle = principal.getName();
        List<Jibber> jibbers = jibberService.getJibberHomeByHandle(handle);

        model.addAttribute("handle", handle);
        model.addAttribute("jibbers", jibbers);
        model.addAttribute("tools", new Tools());
        model.addAttribute("jabber", new Jibber());

        return "home";
    }

    @GetMapping("/{handle:^(?!user$|home$).*}")
    public String showUserWall(@PathVariable String handle, Model model) {
        List<Jibber> jibbers = jibberService.getJibbersByUserHandle(handle);

        model.addAttribute("handle", handle);
        model.addAttribute("jibbers", jibbers);
        model.addAttribute("tools", new Tools());

        return "home";
    }

    @PostMapping("/jibber")
    public String doHomeJibber(@ModelAttribute Jibber jabber, BindingResult bindingResult, Principal principal) {
        User currentUser = userService.getByHandle(principal.getName());

        jabber.setCreatedDate(new Date());
        jabber.setUser(currentUser);
        jibberService.save(jabber);
        notificationService.sendNotification(currentUser, jabber.getMessage());
        return "redirect:/";
    }

    @PostMapping("/{handle:^(?!user$|home$).*}/jibber")
    public String doWallJibber(@PathVariable String handle, @ModelAttribute Jibber jibber, Authentication authentication) {
        return "redirect:/";
    }
}
