package com.codingchallenge.subscriptionsystem.emailservice.config;

import com.codingchallenge.subscriptionsystem.common.model.NewsletterNotification;
import com.codingchallenge.subscriptionsystem.common.model.NewsletterNotificationEvent;
import com.codingchallenge.subscriptionsystem.common.model.User;
import com.codingchallenge.subscriptionsystem.emailservice.service.IEmailService;
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
    private IEmailService emailService;

    private static final boolean EMAIL_SERVICE_RECV_OK = true;

    @PostConstruct
    private void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Bean(name="emailService")
    IEmailService emailServiceMock() {
        Mockito.doAnswer((InvocationOnMock invocationOnMock) -> {
            System.out.println("EMAIL SERVICE: Sending newsletter emails...");
            return true;
        }).when(emailService).sendEmailNewsletterNotification(Mockito.any(NewsletterNotification.class), Mockito.any(User.class));

        return emailService;
    }

}
