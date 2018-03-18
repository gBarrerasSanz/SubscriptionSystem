package com.codingchallenge.subscriptionsystem.eventservice.controller;

import com.codingchallenge.subscriptionsystem.common.Endpoints;
import com.codingchallenge.subscriptionsystem.common.config.Messages;
import com.codingchallenge.subscriptionsystem.common.messages.emailservice.EmSSendEmailNewsletterNotificationRequest;
import com.codingchallenge.subscriptionsystem.common.messages.emailservice.EmSSendEmailNewsletterNotificationResponse;
import com.codingchallenge.subscriptionsystem.common.messages.eventservice.EvSReceiveNewsletterNotificationRequest;
import com.codingchallenge.subscriptionsystem.common.messages.eventservice.EvSReceiveNewsletterNotificationResponse;
import com.codingchallenge.subscriptionsystem.common.messages.eventservice.EvSRegisterSubscriptionRequest;
import com.codingchallenge.subscriptionsystem.common.messages.eventservice.EvSRegisterSubscriptionResponse;
import com.codingchallenge.subscriptionsystem.common.model.NewsletterNotification;
import com.codingchallenge.subscriptionsystem.common.model.User;
import com.codingchallenge.subscriptionsystem.eventservice.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Rest Controller for the Event Service
 */
@RestController
@RequestMapping("/eventservice")
public class EventServiceController {

    @Autowired
    @Qualifier("eventService")
    IEventService eventService;

    @Autowired
    Messages messages;

    @RequestMapping(
            value = "/registerSubscription",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EvSRegisterSubscriptionResponse> registerSubscription(
            @RequestBody EvSRegisterSubscriptionRequest EvSRegisterSubscriptionRequest) {

        if (eventService.registerSubscription(EvSRegisterSubscriptionRequest.getSubscription().getUser(),
                EvSRegisterSubscriptionRequest.getSubscription().getNewsletter())) {
            return new ResponseEntity<>(
                    new EvSRegisterSubscriptionResponse(true), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(
                    new EvSRegisterSubscriptionResponse(false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            value = "/receiveNewsletterNotification",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EvSReceiveNewsletterNotificationResponse> receiveNewsletterNotificationRequest(
            @RequestBody EvSReceiveNewsletterNotificationRequest evSReceiveNewsletterNotificationRequest) {

        if (eventService.receiveNewsletterNotificationRequest(
                evSReceiveNewsletterNotificationRequest.getNewsletterNotificationEvent())) {

            scheduleMailSending(evSReceiveNewsletterNotificationRequest
                    .getNewsletterNotificationEvent().getNewsletterNotification());
            return new ResponseEntity<>(
                    new EvSReceiveNewsletterNotificationResponse(true), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(
                    new EvSReceiveNewsletterNotificationResponse(false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Async
    private void scheduleMailSending(NewsletterNotification newsletterNotification){
        List<User> lstUsersToInform = eventService.getUsersToInform(newsletterNotification.getNewsletter());
        for (User user: lstUsersToInform) {
            sendNewsletterNotificationMail(newsletterNotification, user);
        }
    }

    private void sendNewsletterNotificationMail(NewsletterNotification newsletterNotification, User user){
        EmSSendEmailNewsletterNotificationRequest emSSendEmailNewsletterNotificationRequest =
                new EmSSendEmailNewsletterNotificationRequest(newsletterNotification, user);
        ResponseEntity<EmSSendEmailNewsletterNotificationResponse> emSSendEmailNewsletterNotificationResponse = null;
        try {
            emSSendEmailNewsletterNotificationResponse = new RestTemplate()
                    .postForEntity(Endpoints.EMS_SEND_EMAIL_NEWSLETTER, emSSendEmailNewsletterNotificationRequest,
                            EmSSendEmailNewsletterNotificationResponse.class);
            if (emSSendEmailNewsletterNotificationResponse.getStatusCode().equals(HttpStatus.ACCEPTED)){
                System.out.println(messages.get("component.eventservice.sendnewsletter_notification_email.ok"));
            }
        } catch(HttpStatusCodeException e) {
            System.err.println(messages.get("component.eventservice.sendnewsletter_notification_email.error"));
        } catch(ResourceAccessException e) {
            System.err.println(messages.get("component.subscriptionmanagerservice.registeringevent.error.not_available"));
        }
    }


}
