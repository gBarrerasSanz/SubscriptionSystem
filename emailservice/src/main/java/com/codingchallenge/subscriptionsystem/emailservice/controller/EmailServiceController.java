package com.codingchallenge.subscriptionsystem.emailservice.controller;

import com.codingchallenge.subscriptionsystem.common.messages.emailservice.EmSSendEmailNewsletterNotificationRequest;
import com.codingchallenge.subscriptionsystem.common.messages.emailservice.EmSSendEmailNewsletterNotificationResponse;
import com.codingchallenge.subscriptionsystem.emailservice.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for the Email Service
 */
@RestController
@RequestMapping("/emailservice")
public class EmailServiceController {

    @Autowired
    @Qualifier("emailService")
    IEmailService emailService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @RequestMapping(
            value = "/sendEmailNewsletterNotification",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmSSendEmailNewsletterNotificationResponse> sendEmailNewsletterNotification(
            @RequestBody EmSSendEmailNewsletterNotificationRequest esReq)
    {
        if (emailService.sendEmailNewsletterNotification(esReq.getNewsletterNotification(), esReq.getUser())) {
            return new ResponseEntity<>(
                    new EmSSendEmailNewsletterNotificationResponse(true), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(
                    new EmSSendEmailNewsletterNotificationResponse(true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
