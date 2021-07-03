package com.javafanatics.jibberjabber.account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("FROM User u WHERE u.email = :email OR u.handle = :handle")
    List<User> findByMailOrHandle(@Param("email") String email, @Param("handle") String handle);

    User getByEmail(String s);
    User getByHandle(String s);
}
