package com.javafanatics.jibberjabber.account;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.security.Principal;

@Entity @Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String handle;
    private String password;
    private boolean enabled;
    private String role;
}
