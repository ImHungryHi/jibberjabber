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

    // Justification for the use of UserValidationException above all others - this allows for multiple exceptions simultaneously
    @Override
    //public void save(User user) throws PasswordConfirmationException, PasswordConfirmationEmptyException, DuplicateMailException, DuplicateHandleException {
    public void save(User user) throws UserValidationException {
        StringBuilder strExceptions = new StringBuilder();
        String passConfirmation = user.getPasswordConfirmation();

        if (user.getEmail() == null || user.getEmail().equals("")) {
            strExceptions.append("[EmptyEmailException] Mail address not provided");
        }

        if (user.getHandle() == null || user.getHandle().length() < 3) {
            if (strExceptions.length() > 0) {
                strExceptions.append("\n");
            }

            strExceptions.append("[HandleLengthException] User handle empty or not long enough");
        }

        if (user.getPassword() == null || user.getPassword().length() < 8) {
            if (strExceptions.length() > 0) {
                strExceptions.append("\n");
            }

            strExceptions.append("[PasswordLengthException] User password empty or not long enough");
        }

        if (!user.getPassword().equals(passConfirmation)) {
            //throw new PasswordConfirmationException("Passwords do not match");
            if (strExceptions.length() > 0) {
                strExceptions.append("\n");
            }

            strExceptions.append("[PasswordConfirmationException] Passwords do not match");
        }

        if (passConfirmation == null || passConfirmation.equals("")) {
            //throw new PasswordConfirmationEmptyException("Password confirmation turned up empty");
            if (strExceptions.length() > 0) {
                strExceptions.append("\n");
            }

            strExceptions.append("[PasswordConfirmationEmptyException] Password confirmation turned up empty");
        }

        List<User> results = userRepository.findByMailOrHandle(user.getEmail(), user.getHandle());

        // If size == 0 -> this is skipped
        for (int x = 0; x < results.size(); x++) {
            User u = results.get(x);

            if (u.getEmail().equals(user.getEmail())) {
                //throw new DuplicateMailException("Email is already taken");
                if (strExceptions.length() > 0) {
                    strExceptions.append("\n");
                }

                strExceptions.append("[DuplicateMailException] Email is already taken");
            }
            else if (u.getHandle().equals(user.getHandle())) {
                //throw new DuplicateHandleException("Handle is already taken");
                if (strExceptions.length() > 0) {
                    strExceptions.append("\n");
                }

                strExceptions.append("[DuplicateHandleException] Handle is already taken");
            }
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (strExceptions.length() > 0) {
            throw new UserValidationException(strExceptions.toString());
        }

        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /*//@Override
    public void authenticate(User user) throws PasswordMismatchException {
        // We should only be able to get 1 result because:
        //   registration only allows unique handles/mail addresses
        //   the form input is either a mail address or a handle, not both
        User dbUser = userRepository.findByMailOrHandle(user.getHandle(), user.getHandle()).get(0);
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        if (dbUser.getPassword().equals(encodedPassword)) {
            throw new PasswordMismatchException("Passwords do not match");
        }
    }*/
}
