package com.codingchallenge.subscriptionsystem.common.messages.persistenceservice;

import com.codingchallenge.subscriptionsystem.common.model.Subscription;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class PSExistsSubscriptionRequest {
    @JsonInclude
    private Subscription subscription;
}
