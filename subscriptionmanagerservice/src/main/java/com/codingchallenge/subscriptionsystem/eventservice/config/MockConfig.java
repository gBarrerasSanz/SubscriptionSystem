package com.codingchallenge.subscriptionsystem.eventservice.config;

import com.codingchallenge.subscriptionsystem.common.model.Subscription;
import com.codingchallenge.subscriptionsystem.eventservice.service.cache.ICacheService;
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
    private ICacheService cacheService;

    @PostConstruct
    private void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Bean(name="cacheService")
    ICacheService cacheServiceMock() {
        Mockito.doAnswer((InvocationOnMock invocationOnMock) -> {
                System.out.println("CACHE SERVICE: Access cache");
                return null;
        }).when(cacheService).getSubscriptionFromCache(Mockito.any(Subscription.class));

        return cacheService;
    }

}
