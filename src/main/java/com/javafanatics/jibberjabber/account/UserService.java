package com.javafanatics.jibberjabber.account;

import java.util.List;

public interface UserService {
    //void save(User user) throws PasswordConfirmationException, PasswordConfirmationEmptyException, PasswordMismatchException, DuplicateMailException, DuplicateHandleException;
    void save(User user) throws UserValidationException;
    List<User> getAll();
    //void authenticate(User user) throws PasswordMismatchException;

    class UserValidationException extends Exception {
        public UserValidationException(String message) {
            super(message);
        }
    }

    class PasswordConfirmationException extends Exception {
        public PasswordConfirmationException(String message) {
            super(message);
        }
    }

    class PasswordConfirmationEmptyException extends Exception {
        public PasswordConfirmationEmptyException(String message) {
            super(message);
        }
    }

    class PasswordMismatchException extends Exception {
        public PasswordMismatchException(String message) {
            super(message);
        }
    }

    class DuplicateMailException extends Exception {
        public DuplicateMailException(String message) {
            super(message);
        }
    }

    class DuplicateHandleException extends Exception {
        public DuplicateHandleException(String message) {
            super(message);
        }
    }
}
