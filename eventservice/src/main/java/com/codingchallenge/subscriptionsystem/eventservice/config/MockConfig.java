package com.codingchallenge.subscriptionsystem.eventservice.config;

import com.codingchallenge.subscriptionsystem.common.model.Newsletter;
import com.codingchallenge.subscriptionsystem.common.model.NewsletterNotificationEvent;
import com.codingchallenge.subscriptionsystem.common.model.User;
import com.codingchallenge.subscriptionsystem.eventservice.service.IEventService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MockConfig {

    @Mock
    private IEventService eventService;

    @PostConstruct
    private void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Bean(name="eventService")
    IEventService eventServiceMock() {
        Mockito.doAnswer((InvocationOnMock invocationOnMock) -> {
                System.out.println("component.eventservice.register_subscription");
                return true;
        }).when(eventService).registerSubscription(Mockito.any(User.class), Mockito.any(Newsletter.class));

        Mockito.doAnswer((InvocationOnMock invocationOnMock) -> {
                System.out.println("component.eventservice.receive_newsletter_notif_req");
                return true;
        }).when(eventService).receiveNewsletterNotificationRequest(Mockito.any(NewsletterNotificationEvent.class));

        return eventService;
    }

}
