package com.codingchallenge.subscriptionsystem.persistenceservice.model.exceptions;

public class SubscriptionIdDuplicateException extends Exception {
    @Override
    public String getMessage() {
        return "Subscription Id already exists";
    }
}
