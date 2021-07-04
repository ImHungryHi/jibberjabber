package com.javafanatics.jibberjabber.account;
import com.javafanatics.jibberjabber.message.Jibber;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

// Opted to let validation be handled by the save function as it allows simultaneous validation of every field
//   commented out all validation annotations
//@Check(constraints = "password_check IS password")    // in case of a separate object
@Entity @Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@Email(message = "{register.email.format}")
    //@NotEmpty(message = "{register.email.empty}")
    private String email;

    //@Size(min = 3, message = "{register.handle}")
    private String handle;

    //@Column(name = "password")
    //@Size(min = 8, message = "{register.password}")
    private String password;

    @Column(name = "password_check")
    //@NotEmpty(message = "{register.password_check.empty}")
    @Transient // This field is NOT to be added to the database
    private String passwordConfirmation;

    private boolean enabled = true;
    private String role = "USER";

    @OneToMany(mappedBy = "user")
    private List<Jibber> jibbers;
}
