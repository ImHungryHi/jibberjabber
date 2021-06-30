package com.javafanatics.jibberjabber.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AuthenticationController {
    AuthenticationManager authManager;
    UserService userService;

    @Autowired
    public void setAuthManager(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // Let login handle the "/" mapping, with redirect if logged in
    @GetMapping("/login")
    public String showLogin() {
        return "account/login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "account/register";
    }

    // Google a way to look for extra fields that don't occur in entities
    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "account/register";
        }

        String email = user.getEmail();
        String handle = user.getHandle();
        String password = user.getPassword();
        String passwordConfirmation = user.getPasswordConfirmation();
        int existsResult = userService.existsByMailHandle(email, handle);

        if (existsResult == 1) {
            bindingResult.rejectValue("email", "register.email.taken");
        }
        else if (existsResult > 1) {
            bindingResult.rejectValue("handle", "register.handle.taken");
        }

        if (!password.equals(passwordConfirmation)) {
            bindingResult.rejectValue("passwordConfirmation", "register.password_confirmation.mismatch");
        }

        if (!bindingResult.hasErrors()) {
            userService.register(email, handle, password);
            authWithAuthManager(request, handle, password);
            return "redirect:/home";
        }

        return "account/register";
    }

    public void authWithAuthManager(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
