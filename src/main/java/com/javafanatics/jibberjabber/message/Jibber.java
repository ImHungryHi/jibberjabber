package com.javafanatics.jibberjabber.message;
import com.javafanatics.jibberjabber.account.User;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity @Table(name = "jibbers")
@Getter @Setter
public class Jibber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_date")
    private Date createdDate;

    private String message;

    @ManyToOne
    @JoinColumn(name = "parent_jibber_id")
    private Jibber parentJibber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
