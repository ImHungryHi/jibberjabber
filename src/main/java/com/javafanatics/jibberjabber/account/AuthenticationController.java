package com.javafanatics.jibberjabber.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.javafanatics.jibberjabber.account.UserService.*;

@Controller
public class AuthenticationController {
    UserService userService;

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

        try {
            userService.save(user);
        }
        catch(UserValidationException ex) {
            String msg = ex.getMessage();

            if (msg.contains("PasswordConfirmationEmptyException")) {
                bindingResult.rejectValue("passwordConfirmation", "register.password_check.empty");
            }
            else if (msg.contains("PasswordConfirmationException")) {
                bindingResult.rejectValue("passwordConfirmation", "register.password_check.mismatch");
            }

            if (msg.contains("HandleLengthException")) {
                bindingResult.rejectValue("handle", "register.handle");
            }
            if (msg.contains("PasswordLengthException")) {
                bindingResult.rejectValue("password", "register.password");
            }
            if (msg.contains("EmptyEmailException")) {
                bindingResult.rejectValue("email", "register.email.empty");
            }
            if (msg.contains("PasswordMismatchException")) {
                bindingResult.rejectValue("password", "register.password");
            }
            if (msg.contains("DuplicateMailException")) {
                bindingResult.rejectValue("email", "register.email.taken");
            }
            if (msg.contains("DuplicateHandleException")) {
                bindingResult.rejectValue("handle", "register.handle.taken");
            }
        }
        /*
        catch(PasswordConfirmationException ex) { bindingResult.rejectValue("passwordConfirmation", "register.password_confirmation.mismatch"); }
        catch(PasswordConfirmationEmptyException ex) { bindingResult.rejectValue("passwordConfirmation", "register.password_confirmation.empty"); }
        catch(PasswordMismatchException ex) { bindingResult.rejectValue("password", "register.password"); }
        catch(DuplicateMailException ex) { bindingResult.rejectValue("email", "register.email.taken"); }
        catch(DuplicateHandleException ex) { bindingResult.rejectValue("handle", "register.handle.taken"); }
        */

        if (!bindingResult.hasErrors()) {
            //authWithAuthManager(request, user.getEmail(), user.getPassword());
            // After saving, we'll have an encrypted password so we'll choose the unencrypted "password confirmation" to log in
            authWithHttpServletRequest(request, user.getEmail(), user.getPasswordConfirmation());
            return "redirect:/";
        }

        return "account/register";
    }

    public void authWithHttpServletRequest(HttpServletRequest request, String email, String password) {
        try {
            request.login(email, password);
        } catch (ServletException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*public void authWithAuthManager(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }*/
}
