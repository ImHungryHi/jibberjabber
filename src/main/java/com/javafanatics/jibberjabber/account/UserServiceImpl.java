package com.javafanatics.jibberjabber.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

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
    public boolean authenticate(String login, String password) {
        User user = userRepository.findFirstByEmail(login);

        if (user == null) {
            user = userRepository.findFirstByHandle(login);

            if (user == null) {
                return false;
            }
        }

        // Encrypt password & compare
        String encoded = passwordEncoder.encode(password);
        return encoded.equals(user.getPassword());
    }

    @Override
    public void register(String email, String handle, String password) {
        User user = new User();
        user.setEmail(email);
        user.setHandle(handle);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
    }

    @Override
    public int existsByMailHandle(String email, String handle) {
        User user = userRepository.findFirstByEmail(email);

        if (user != null) {
            return 1;
        }

        user = userRepository.findFirstByHandle(handle);

        if (user != null) {
            return 2;
        }

        return 0;
    }
}
