package com.javafanatics.jibberjabber.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
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
    public ModelAndView showLogin() {
        ModelAndView modelView = new ModelAndView("account/login");
        modelView.addObject("loginForm", new LoginForm());
        return modelView;
    }

    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "account/login";
        }

        String handle = loginForm.getHandle();
        String password = loginForm.getPassword();

        if (userService.authenticate(handle, password)) {
            authWithAuthManager(request, handle, password);
            return "redirect:/home";
        }

        bindingResult.rejectValue("handle", "login.invalid");
        return "account/login";
    }

    @GetMapping("/register")
    public ModelAndView showRegister() {
        ModelAndView modelView = new ModelAndView("account/register");
        modelView.addObject("registerForm", new RegisterForm());
        return modelView;
    }

    // Google a way to look for extra fields that don't occur in entities
    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute RegisterForm registerForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "account/register";
        }

        String email = registerForm.getEmail();
        String handle = registerForm.getHandle();
        String password = registerForm.getPassword();
        String passwordConfirmation = registerForm.getPasswordConfirmation();
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

    @GetMapping("/logout")
    public String doLogout() {
        // Logout
        return "redirect:/login";
    }

    public void authWithAuthManager(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
