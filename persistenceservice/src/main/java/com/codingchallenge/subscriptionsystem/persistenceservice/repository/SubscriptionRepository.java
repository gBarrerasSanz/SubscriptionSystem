package com.codingchallenge.subscriptionsystem.persistenceservice.repository;

import com.codingchallenge.subscriptionsystem.common.model.Newsletter;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.NewsletterEntity;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.SubscriptionEntity;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, String> {

    Optional<SubscriptionEntity> findById(String id);
    Optional<SubscriptionEntity> findByUserAndNewsletter(UserEntity user, NewsletterEntity newsletter);

    SubscriptionEntity save(SubscriptionEntity subscriptionEntity);

}
