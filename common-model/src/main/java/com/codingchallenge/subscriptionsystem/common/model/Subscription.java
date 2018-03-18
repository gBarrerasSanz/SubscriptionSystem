package com.codingchallenge.subscriptionsystem.common.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subscription implements ISubscription{

    @JsonInclude
    private String subscriptionId;
    @JsonInclude
    private User user;
    @JsonInclude
    private Newsletter newsletter;
}
