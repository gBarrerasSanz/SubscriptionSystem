package com.codingchallenge.subscriptionsystem.persistenceservice.controller;

import com.codingchallenge.subscriptionsystem.common.config.Messages;
import com.codingchallenge.subscriptionsystem.common.messages.persistenceservice.PSCreateSubscriptionRequest;
import com.codingchallenge.subscriptionsystem.common.messages.persistenceservice.PSCreateSubscriptionResponse;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.SubscriptionEntity;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.exceptions.GenericSubscriptionCreationException;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.exceptions.NewsletterNotFoundException;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.exceptions.SubscriptionIdDuplicateException;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.exceptions.UserEmailAndNewsletterIdDuplicateException;
import com.codingchallenge.subscriptionsystem.persistenceservice.service.SubscriptionPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for the Persistence Service
 */
@RestController
@RequestMapping("/persistenceservice")
public class PersistenceController {

    @Autowired
    private SubscriptionPersistenceService persistenceService;

    @Autowired
    Messages messages;

    @RequestMapping(
            value = "/subscriptions",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSubscription(@RequestBody PSCreateSubscriptionRequest request) {
        try {
            SubscriptionEntity createdSubscriptionEntity = persistenceService.create(new SubscriptionEntity(request.getSubscription()));
            if (createdSubscriptionEntity == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            System.out.println(messages.get("component.persistencemanager.create_subscription.ok"));
            return new ResponseEntity<>(
                    new PSCreateSubscriptionResponse(createdSubscriptionEntity.toSubcription()), HttpStatus.CREATED);
        } catch (NewsletterNotFoundException e) {
            System.out.println(messages.get("component.persistencemanager.create_subscription.error")+"="+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UserEmailAndNewsletterIdDuplicateException e) {
            System.out.println(messages.get("component.persistencemanager.create_subscription.error")+"="+e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (GenericSubscriptionCreationException | SubscriptionIdDuplicateException e) {
            System.out.println(messages.get("component.persistencemanager.create_subscription.error")+"="+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
