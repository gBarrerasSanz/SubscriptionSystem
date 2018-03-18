package com.codingchallenge.subscriptionsystem.eventservice.service;

import com.codingchallenge.subscriptionsystem.common.Endpoints;
import com.codingchallenge.subscriptionsystem.common.config.Messages;
import com.codingchallenge.subscriptionsystem.common.messages.eventservice.EvSRegisterSubscriptionRequest;
import com.codingchallenge.subscriptionsystem.common.messages.eventservice.EvSRegisterSubscriptionResponse;
import com.codingchallenge.subscriptionsystem.common.messages.persistenceservice.PSCreateSubscriptionRequest;
import com.codingchallenge.subscriptionsystem.common.messages.persistenceservice.PSCreateSubscriptionResponse;
import com.codingchallenge.subscriptionsystem.common.model.Newsletter;
import com.codingchallenge.subscriptionsystem.common.model.Subscription;
import com.codingchallenge.subscriptionsystem.common.model.User;
import com.codingchallenge.subscriptionsystem.eventservice.exceptions.*;
import com.codingchallenge.subscriptionsystem.eventservice.service.cache.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


@Component("subscriptionmanagerService")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SubscriptionManagerService implements ISubscriptionManagerService{

    @Autowired
    @Qualifier("cacheService")
    ICacheService cacheService;

    @Autowired
    Messages messages;

    @Override
    public Subscription createSubscription(User user, Newsletter newsletter) throws SubscriptionManagerServiceException {
        Subscription requestSubscription = buildRequestSubscription(user, newsletter);

        if (cacheService.getSubscriptionFromCache(requestSubscription) != null) {
            throw new SubscriptionManagerServiceAlreadyExistsException("Subscription already exists");
        }
        Subscription createdSubscription = createSubscription(requestSubscription);
        if (createdSubscription != null){
            cacheService.addSubscriptionToCache(createdSubscription);
        }

        if (user.getConsent()) {
            registerSubscriptionEvent(createdSubscription);
        }
        return createdSubscription;
    }

    private Subscription buildRequestSubscription(User user, Newsletter newsletter) throws SubscriptionManagerServiceClientException {
        if ( user == null
                || newsletter == null
                || ! user.validateConsistency()
                || ! newsletter.validateConsistency()) {
            throw new SubscriptionManagerServiceClientException("Required information not complete");
        }
        return new Subscription(UUID.randomUUID().toString(), user, newsletter);
    }

    private Subscription createSubscription(Subscription subscription) throws SubscriptionManagerServiceException {
        PSCreateSubscriptionRequest psCreateSubscriptionRequest =
                new PSCreateSubscriptionRequest(subscription);
        ResponseEntity<PSCreateSubscriptionResponse> psCreateSubscriptionResponse = null;
        try {
            psCreateSubscriptionResponse = new RestTemplate()
                    .postForEntity(Endpoints.PS_CREATE_SUBSCRIPTION, psCreateSubscriptionRequest, PSCreateSubscriptionResponse.class);
            if ( psCreateSubscriptionResponse.getStatusCode().equals(HttpStatus.CREATED)){
                return psCreateSubscriptionRequest.getSubscription();
            } else {
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch(HttpStatusCodeException e){
            switch(e.getStatusCode()){
                case CONFLICT:
                    System.err.println(e.getResponseBodyAsString());
                    throw new SubscriptionManagerServiceAlreadyExistsException(e.getResponseBodyAsString());
                case NOT_FOUND:
                    System.err.println(e.getResponseBodyAsString());
                    throw new SubscriptionManagerServiceResourceNotFoundException(e.getResponseBodyAsString());
                case INTERNAL_SERVER_ERROR:
                default:
                    System.err.println(e.getResponseBodyAsString());
                    throw new SubscriptionManagerServiceServerException(e.getResponseBodyAsString());
            }
        } catch(ResourceAccessException e) {
            System.err.println(messages.get("component.persistencemanager.create_subscription.error.not_available"));
            throw new SubscriptionManagerServiceServerException(messages.get("component.persistencemanager.create_subscription.error.not_available"));
        }
    }

    @Async("registerSubscriptionEventTaskExecutor")
    private void registerSubscriptionEvent(Subscription subscription){
        EvSRegisterSubscriptionRequest evSRegisterSubscriptionRequest =
                new EvSRegisterSubscriptionRequest(subscription);
        ResponseEntity<EvSRegisterSubscriptionResponse> evSRegisterSubscriptionResponse = null;
        try {
            evSRegisterSubscriptionResponse = new RestTemplate()
                    .postForEntity(Endpoints.EVS_REGISTER_SUBSCRIPTION, evSRegisterSubscriptionRequest, EvSRegisterSubscriptionResponse.class);
            if (evSRegisterSubscriptionResponse.getStatusCode().equals(HttpStatus.ACCEPTED)){
                System.out.println(messages.get("component.subscriptionmanagerservice.registeringevent.ok"));
            }
        } catch(HttpStatusCodeException e){
            System.err.println(messages.get("component.subscriptionmanagerservice.registeringevent.error"));
        } catch(ResourceAccessException e) {
            System.err.println(messages.get("component.subscriptionmanagerservice.registeringevent.error.not_available"));
        }
    }
}
