package com.codingchallenge.subscriptionsystem.common.messages.subscriptionmanagerservice;

import com.codingchallenge.subscriptionsystem.common.model.Newsletter;
import com.codingchallenge.subscriptionsystem.common.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class SMCreateSubscriptionRequest {
    private User user;
    private Newsletter newsletter;
}
