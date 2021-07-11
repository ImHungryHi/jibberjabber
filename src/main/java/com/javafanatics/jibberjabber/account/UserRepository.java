package com.javafanatics.jibberjabber.account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("FROM User u WHERE u.email = :email OR u.handle = :handle")
    List<User> findByMailOrHandle(@Param("email") String email, @Param("handle") String handle);

    @Query("FROM User u LEFT JOIN FETCH u.follows WHERE u.handle = :handle")
    User findByHandle(@Param("handle") String handle);

    @Query("SELECT u.handle FROM User u WHERE u.email = :email")
    String getHandleByMail(@Param("email") String email);

    @Query("FROM User u LEFT OUTER JOIN FETCH u.follows r WHERE u.id != :id GROUP BY u.id")
    List<User> findAllOtherUsers(@Param("id") int id);

    User getByEmail(String s);
    User getByHandle(String s);
}
