package com.codingchallenge.subscriptionsystem.eventservice.controller;

import com.codingchallenge.subscriptionsystem.common.messages.subscriptionmanagerservice.SMCreateSubscriptionRequest;
import com.codingchallenge.subscriptionsystem.common.messages.subscriptionmanagerservice.SMCreateSubscriptionResponse;
import com.codingchallenge.subscriptionsystem.common.model.Subscription;
import com.codingchallenge.subscriptionsystem.eventservice.exceptions.*;
import com.codingchallenge.subscriptionsystem.eventservice.service.ISubscriptionManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for the Subscription Manager Service
 */
@RestController
@RequestMapping("/subscriptionmanagerservice")
public class SubcriptionManagerServiceController {

    @Autowired
    @Qualifier("subscriptionmanagerService")
    ISubscriptionManagerService subscriptionmanagerService;

    @RequestMapping(
            value = "/createSubscription",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSubscription(
            @RequestBody SMCreateSubscriptionRequest smCreateSubscriptionRequest) {
        try {
            Subscription subscription = subscriptionmanagerService.createSubscription(smCreateSubscriptionRequest.getUser(),
                    smCreateSubscriptionRequest.getNewsletter());
                return new ResponseEntity(
                        new SMCreateSubscriptionResponse(subscription.getSubscriptionId()), HttpStatus.CREATED);
        } catch(SubscriptionManagerServiceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch(SubscriptionManagerServiceResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }  catch(SubscriptionManagerServiceClientException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(SubscriptionManagerServiceServerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch(SubscriptionManagerServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
