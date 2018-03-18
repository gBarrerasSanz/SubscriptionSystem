package com.codingchallenge.subscriptionsystem.api.controller;

import com.codingchallenge.subscriptionsystem.common.Endpoints;
import com.codingchallenge.subscriptionsystem.common.config.Messages;
import com.codingchallenge.subscriptionsystem.common.messages.api.ApiCreateSubscriptionRequest;
import com.codingchallenge.subscriptionsystem.common.messages.api.ApiCreateSubscriptionResponse;
import com.codingchallenge.subscriptionsystem.common.messages.subscriptionmanagerservice.SMCreateSubscriptionRequest;
import com.codingchallenge.subscriptionsystem.common.messages.subscriptionmanagerservice.SMCreateSubscriptionResponse;
import com.codingchallenge.subscriptionsystem.common.model.Newsletter;
import com.codingchallenge.subscriptionsystem.common.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Rest Controller for the public Subscription API
 */
@RestController
@RequestMapping("/api")
//@Api(value = "/api", description = "Api for subscriptions")
@Api(value = "/api", description = "Api for subscriptions")
public class SubscriptionAPIController {

    @Autowired
    Messages messages;

    @ApiOperation(httpMethod = "POST",
            value = "Creates a subscription",
            response = ApiCreateSubscriptionResponse.class,
            nickname = "cteateSubscription")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful subsciption creation", response = ApiCreateSubscriptionResponse.class),
            @ApiResponse(code = 400, message = "Client error: Required information is not complete"),
            @ApiResponse(code = 404, message = "Client error: Newsletter is not available for subscribing"),
            @ApiResponse(code = 409, message = "Client error: Account already registered to the newsletter"),
            @ApiResponse(code = 500, message = "Server error: Subscription could not be created")
    })
    @RequestMapping(
            value = "/subscriptions",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSubscription(
            @RequestBody ApiCreateSubscriptionRequest apiCreateSubscriptionRequest)
    {
        if ( ! validateRequestBody(apiCreateSubscriptionRequest)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages.get("component.api.create_subscription.error.validate_input"));
        }
        ResponseEntity<SMCreateSubscriptionResponse> smCreateSubscriptionResponseResponseEntity = null;
        try {
            smCreateSubscriptionResponseResponseEntity =
                    postRequest(apiCreateSubscriptionRequest.getUser(), apiCreateSubscriptionRequest.getNewsletter());
            if ( smCreateSubscriptionResponseResponseEntity.getStatusCode().equals(HttpStatus.CREATED)){
                System.out.println(messages.get("component.api.create_subscription.ok"));
                return new ResponseEntity<>(new ApiCreateSubscriptionResponse(smCreateSubscriptionResponseResponseEntity.getBody().getSubscriptionId()),
                        HttpStatus.CREATED);
            } else {
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch(HttpStatusCodeException e){
            switch(e.getStatusCode()){
                case CONFLICT:
                    System.err.println(messages.get("component.api.create_subscription.error.conflict"));
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
                case NOT_FOUND:
                    System.err.println(messages.get("component.api.create_subscription.error.not_found"));
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
                case INTERNAL_SERVER_ERROR:
                default:
                    System.err.println(messages.get("component.api.create_subscription.error.server_error"));
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        } catch(ResourceAccessException e) {
            System.err.println(messages.get("component.subscriptionmanagerservice.create_subscription.error.not_available"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messages.get("component.subscriptionmanagerservice.create_subscription.error.not_available"));
        }

    }


    private static ResponseEntity<SMCreateSubscriptionResponse> postRequest(User user, Newsletter newsletter) {
        SMCreateSubscriptionRequest smCreateSubscriptionRequest =
                new SMCreateSubscriptionRequest(user, newsletter);
        return new RestTemplate()
                .postForEntity(Endpoints.SMS_CREATE_SUBSCRIPTION,
                        smCreateSubscriptionRequest, SMCreateSubscriptionResponse.class);
    }


    private boolean validateRequestBody(ApiCreateSubscriptionRequest apiCreateSubscriptionRequest){
        return apiCreateSubscriptionRequest.getUser() != null
                && apiCreateSubscriptionRequest.getNewsletter() != null
                && apiCreateSubscriptionRequest.getUser().validateConsistency()
                && apiCreateSubscriptionRequest.getNewsletter().validateConsistency();
    }
}
