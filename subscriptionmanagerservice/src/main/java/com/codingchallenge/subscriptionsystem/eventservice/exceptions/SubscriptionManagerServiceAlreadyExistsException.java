package com.codingchallenge.subscriptionsystem.eventservice.exceptions;

public class SubscriptionManagerServiceAlreadyExistsException extends SubscriptionManagerServiceClientException {
    public SubscriptionManagerServiceAlreadyExistsException(String msg) {
        super(msg);
    }
}
