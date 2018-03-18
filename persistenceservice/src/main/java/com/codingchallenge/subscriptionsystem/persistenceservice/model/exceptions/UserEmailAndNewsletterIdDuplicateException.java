package com.codingchallenge.subscriptionsystem.persistenceservice.model.exceptions;

public class UserEmailAndNewsletterIdDuplicateException extends Exception {
    @Override
    public String getMessage() {
        return "Account is already registered";
    }
}
