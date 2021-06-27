package com.javafanatics.jibberjabber.account;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity @Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String handle;

    @NotEmpty
    private String password;

    private boolean enabled;
    private String role;
}
