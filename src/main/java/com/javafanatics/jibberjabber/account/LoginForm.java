package com.javafanatics.jibberjabber.account;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class LoginForm {
    @NotEmpty(message = "{login.handle.empty}")
    private String handle;

    @NotEmpty(message = "{login.password.empty}")
    private String password;
}
