package com.javafanatics.jibberjabber.account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findFirstByEmail(String email);
    User findFirstByHandle(String handle);
}
