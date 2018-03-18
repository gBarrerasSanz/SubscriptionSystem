package com.codingchallenge.subscriptionsystem.persistenceservice.config;

import com.codingchallenge.subscriptionsystem.common.model.IUser;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.NewsletterEntity;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.SubscriptionEntity;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.UserEntity;
import com.codingchallenge.subscriptionsystem.persistenceservice.repository.SubscriptionRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class RepositoryInitialData {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Bean
    InitializingBean sendDatabase() {
        return() -> {
            UserEntity user = new UserEntity("user01@gmail.com", new Date(), true, "user 01", IUser.Gender.MALE);
            NewsletterEntity newsletter = new NewsletterEntity("newsletter01", "newsletter01");
            SubscriptionEntity subscription = new SubscriptionEntity("subscriptionId01", user, newsletter);


            subscriptionRepository.save(subscription);
        };
    }
}
