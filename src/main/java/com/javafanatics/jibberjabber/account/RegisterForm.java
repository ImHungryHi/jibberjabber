package com.javafanatics.jibberjabber.account;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterForm {
    private String email;
    private String handle;
    private String password;
    private String passwordConfirmation;
}
