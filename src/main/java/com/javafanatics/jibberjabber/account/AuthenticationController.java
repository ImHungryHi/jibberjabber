package com.javafanatics.jibberjabber.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class AuthenticationController {
    AuthenticationManager authManager;

    @Autowired
    public void setAuthManager(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    // Let login handle the "/" mapping, with redirect if logged in
    @GetMapping("/login")
    public String showLogin() {
        return "account/login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestBody MultiValueMap<String, String> formData, HttpServletRequest request) {
    //public String doLogin(@ModelAttribute User user, BindingResult bindingResult) {
        /*if (bindingResult.hasErrors()) {
            return "/login";
        }*/

        String user = formData.getFirst("email");
        String pass = formData.getFirst("password");

        authWithAuthManager(request, user, pass);

        return "redirect:/home";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "account/register";
    }

    // Google a way to look for extra fields that don't occur in entities
    @PostMapping("/register")
    public String doRegister(@ModelAttribute User user, BindingResult bindingResult) {
        return null;
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
