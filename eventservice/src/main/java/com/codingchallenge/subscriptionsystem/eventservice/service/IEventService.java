package com.codingchallenge.subscriptionsystem.eventservice.service;

import com.codingchallenge.subscriptionsystem.common.model.Newsletter;
import com.codingchallenge.subscriptionsystem.common.model.NewsletterNotificationEvent;
import com.codingchallenge.subscriptionsystem.common.model.User;

import java.util.List;

public interface IEventService {

    boolean registerSubscription(User user, Newsletter newsletter);

    boolean receiveNewsletterNotificationRequest(NewsletterNotificationEvent newsletterNotification);

    List<User> getUsersToInform(Newsletter newsletter);
}
