package com.javafanatics.jibberjabber.account;

public interface UserService {
    boolean authenticate(String login, String password);
    void register(String email, String handle, String password);
    int existsByMailHandle(String email, String handle);
}
