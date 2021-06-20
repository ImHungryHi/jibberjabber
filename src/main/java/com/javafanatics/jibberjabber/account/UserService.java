package com.javafanatics.jibberjabber.account;

public interface UserService {
    boolean authenticate(String login, String password);
}
