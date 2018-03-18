package com.codingchallenge.subscriptionsystem.persistenceservice.model.exceptions;

public class GenericSubscriptionCreationException extends Exception {
    @Override
    public String getMessage() {
        return "Unknown error when creating subscription";
    }
}
