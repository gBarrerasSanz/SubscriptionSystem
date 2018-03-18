package com.codingchallenge.subscriptionsystem.persistenceservice.repository;

import com.codingchallenge.subscriptionsystem.persistenceservice.model.NewsletterEntity;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsletterRepository extends JpaRepository<NewsletterEntity, String> {

    Optional<NewsletterEntity> findById(String newsletterId);
}
