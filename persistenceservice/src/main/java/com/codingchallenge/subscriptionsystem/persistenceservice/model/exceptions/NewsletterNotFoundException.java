package com.codingchallenge.subscriptionsystem.persistenceservice.model.exceptions;

public class NewsletterNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "Newsletter not found";
    }
}
