package com.codingchallenge.subscriptionsystem.common.messages.eventservice;

import com.codingchallenge.subscriptionsystem.common.model.NewsletterNotification;
import com.codingchallenge.subscriptionsystem.common.model.NewsletterNotificationEvent;
import com.codingchallenge.subscriptionsystem.common.model.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvSRegisterSubscriptionRequest {

    private Subscription subscription;
}
