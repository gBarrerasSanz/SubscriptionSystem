package com.codingchallenge.subscriptionsystem.common.messages.eventservice;

import com.codingchallenge.subscriptionsystem.common.model.NewsletterNotificationEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvSReceiveNewsletterNotificationRequest {

    private NewsletterNotificationEvent newsletterNotificationEvent;
}
