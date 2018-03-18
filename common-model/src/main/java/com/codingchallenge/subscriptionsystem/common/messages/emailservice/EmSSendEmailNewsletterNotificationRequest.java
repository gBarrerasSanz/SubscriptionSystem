package com.codingchallenge.subscriptionsystem.common.messages.emailservice;

import com.codingchallenge.subscriptionsystem.common.model.NewsletterNotification;
import com.codingchallenge.subscriptionsystem.common.model.NewsletterNotificationEvent;
import com.codingchallenge.subscriptionsystem.common.model.User;
import lombok.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class EmSSendEmailNewsletterNotificationRequest {
    private NewsletterNotification newsletterNotification;
    private User user;
}
