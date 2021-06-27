package com.javafanatics.jibberjabber.account;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter @Setter
//@Check(constraints = "password_check IS password")
public class RegisterForm {
    @Email(message = "{register.email.format}")
    @NotEmpty(message = "{register.email.empty}")
    private String email;

    @Size(min = 2, message = "{register.handle}")
    private String handle;

    @Column(name = "password")
    @NotEmpty(message = "{register.password}")
    private String password;

    @Column(name = "password_check")
    @NotEmpty(message = "{register.password_check.empty}")
    private String passwordConfirmation;
}
