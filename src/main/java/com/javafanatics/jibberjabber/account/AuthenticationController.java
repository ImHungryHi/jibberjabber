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
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

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
    public String doLogin(@ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
        /*if (bindingResult.hasErrors()) {
            return "/login";
        }*/

        String handle = loginForm.getHandle();
        String password = loginForm.getPassword();

        if (userService.authenticate(handle, password)) {
            authWithAuthManager(request, handle, password);
            return "redirect:/home";
        }

        bindingResult.rejectValue("password", "Given credentials are incorrect");
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
    public String doRegister(@ModelAttribute RegisterForm registerForm, BindingResult bindingResult, HttpServletRequest request) {
        //authWithAuthManager(request, registerForm.getHandle(), registerForm.getPassword());

        return "redirect:/home";
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
