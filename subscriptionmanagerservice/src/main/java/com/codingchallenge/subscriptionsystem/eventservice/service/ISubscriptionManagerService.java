package com.codingchallenge.subscriptionsystem.eventservice.service;

import com.codingchallenge.subscriptionsystem.common.model.Newsletter;
import com.codingchallenge.subscriptionsystem.common.model.NewsletterNotificationEvent;
import com.codingchallenge.subscriptionsystem.common.model.Subscription;
import com.codingchallenge.subscriptionsystem.common.model.User;
import com.codingchallenge.subscriptionsystem.eventservice.exceptions.SubscriptionManagerServiceException;

public interface ISubscriptionManagerService {

    Subscription createSubscription(User user, Newsletter newsletter) throws SubscriptionManagerServiceException;
}
