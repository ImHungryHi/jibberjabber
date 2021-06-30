package com.javafanatics.jibberjabber.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import com.javafanatics.jibberjabber.account.UserService.*;

import java.util.List;

@Repository
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(User user) throws PasswordConfirmationException, DuplicateMailException, DuplicateHandleException {
        if (user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new UserService.PasswordConfirmationException("Passwords do not match");
        }

        List<User> results = userRepository.findByMailOrHandle(user.getEmail(), user.getHandle());

        // If size == 0 -> this is skipped
        for (int x = 0; x < results.size(); x++) {
            User u = results.get(x);

            if (u.getEmail().equals(user.getEmail())) {
                throw new DuplicateMailException("Email is already taken");
            }
            else if (u.getHandle().equals(user.getHandle())) {
                throw new DuplicateHandleException("Handle is already taken");
            }
        }


        /*
        // Encrypt password & compare
        String encoded = passwordEncoder.encode(password);
        return encoded.equals(user.getPassword());
        */
    }
}
