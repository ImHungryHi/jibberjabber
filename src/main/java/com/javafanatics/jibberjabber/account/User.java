package com.javafanatics.jibberjabber.account;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//@Check(constraints = "password_check IS password")    // in case of a separate object
@Entity @Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Email(message = "{register.email.format}")
    @NotEmpty(message = "{register.email.empty}")
    private String email;

    @Size(min = 2, message = "{register.handle}")
    private String handle;

    //@Column(name = "password")
    @Size(min = 8, message = "{register.password}")
    private String password;

    @Column(name = "password_check")
    @NotEmpty(message = "{register.password_check.empty}")
    @Transient // This field is NOT to be added to the database
    private String passwordConfirmation;

    private boolean enabled;
    private String role;
}
