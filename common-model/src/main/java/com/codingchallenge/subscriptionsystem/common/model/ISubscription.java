package com.codingchallenge.subscriptionsystem.common.model;

public interface ISubscription {

    String getSubscriptionId();
    User getUser();
    Newsletter getNewsletter();
}
