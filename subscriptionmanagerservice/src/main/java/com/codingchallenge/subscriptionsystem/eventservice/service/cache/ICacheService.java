package com.codingchallenge.subscriptionsystem.eventservice.service.cache;

import com.codingchallenge.subscriptionsystem.common.model.Subscription;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface ICacheService {

    Subscription getSubscriptionFromCache(Subscription subscription);

    void addSubscriptionToCache(Subscription subscription);
}
