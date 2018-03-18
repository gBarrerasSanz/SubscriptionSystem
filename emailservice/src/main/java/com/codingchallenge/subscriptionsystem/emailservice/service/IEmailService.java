package com.codingchallenge.subscriptionsystem.emailservice.service;

import com.codingchallenge.subscriptionsystem.common.model.NewsletterNotification;
import com.codingchallenge.subscriptionsystem.common.model.NewsletterNotificationEvent;
import com.codingchallenge.subscriptionsystem.common.model.User;

public interface IEmailService {

    boolean sendEmailNewsletterNotification(NewsletterNotification newsletterNotification, User user);
}
