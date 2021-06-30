package com.javafanatics.jibberjabber.account;

public interface UserService {
    class PasswordConfirmationException extends Exception {
        public PasswordConfirmationException(String message) {
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

    void save(User user) throws PasswordConfirmationException, PasswordMismatchException, DuplicateMailException, DuplicateHandleException;
}
