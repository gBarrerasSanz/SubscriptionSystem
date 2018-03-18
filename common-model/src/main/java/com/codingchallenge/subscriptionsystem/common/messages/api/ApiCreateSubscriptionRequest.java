package com.codingchallenge.subscriptionsystem.common.messages.api;

import com.codingchallenge.subscriptionsystem.common.model.Newsletter;
import com.codingchallenge.subscriptionsystem.common.model.User;
import io.swagger.annotations.ApiModel;
import lombok.*;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@ApiModel
public class ApiCreateSubscriptionRequest {
    private User user;
    private Newsletter newsletter;
}
