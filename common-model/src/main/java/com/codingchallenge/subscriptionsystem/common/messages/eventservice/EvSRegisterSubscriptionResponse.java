package com.codingchallenge.subscriptionsystem.common.messages.eventservice;

import com.codingchallenge.subscriptionsystem.common.model.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class EvSRegisterSubscriptionResponse {

    private Boolean result;
}
