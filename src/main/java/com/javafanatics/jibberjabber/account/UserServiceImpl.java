package com.javafanatics.jibberjabber.account;

import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authenticate(String login, String password) {
        User user = userRepository.findFirstByEmail(login);

        if (user == null) {
            user = userRepository.findFirstByHandle(login);

            if (user == null) {
                return false;
            }
        }

        // Encrypt password & compare

        return true;
    }
}
