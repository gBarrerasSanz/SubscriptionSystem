package com.codingchallenge.subscriptionsystem.common.messages.persistenceservice;

import com.codingchallenge.subscriptionsystem.common.model.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class PSGetSubscriptionResponse {
    private Subscription subscription;
}
