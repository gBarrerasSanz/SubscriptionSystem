package com.codingchallenge.subscriptionsystem.persistenceservice.service;


import com.codingchallenge.subscriptionsystem.common.model.Newsletter;
import com.codingchallenge.subscriptionsystem.common.model.Subscription;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.NewsletterEntity;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.exceptions.GenericSubscriptionCreationException;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.exceptions.NewsletterNotFoundException;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.exceptions.SubscriptionIdDuplicateException;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.exceptions.UserEmailAndNewsletterIdDuplicateException;
import com.codingchallenge.subscriptionsystem.persistenceservice.model.SubscriptionEntity;
import com.codingchallenge.subscriptionsystem.persistenceservice.repository.NewsletterRepository;
import com.codingchallenge.subscriptionsystem.persistenceservice.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SubscriptionPersistenceService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private NewsletterRepository newsletterRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public SubscriptionEntity create(SubscriptionEntity subscription) throws UserEmailAndNewsletterIdDuplicateException, DataAccessException, NewsletterNotFoundException, SubscriptionIdDuplicateException, GenericSubscriptionCreationException {
        try {

            if(findNewsletter(subscription.getNewsletterEntity().getNewsletterId()) != null
                    && checkSubscriptionDoesNotExist(subscription))
            {
                return subscriptionRepository.save(subscription);
            } else {
                throw new GenericSubscriptionCreationException();
            }
        } catch(DataAccessException e) {
            throw new GenericSubscriptionCreationException();
        }
    }

    public boolean checkSubscriptionDoesNotExist(SubscriptionEntity subscription) throws UserEmailAndNewsletterIdDuplicateException, SubscriptionIdDuplicateException {
        return checkSubscriptionIdNotDuplicated(subscription)
                && checkUserEmailAndNewsletterIdNotDuplicated(subscription);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public NewsletterEntity findNewsletter(String newsletterId) throws NewsletterNotFoundException {
        Optional<NewsletterEntity> retrievedNewsletter = newsletterRepository.findById(newsletterId);
        if ( retrievedNewsletter.isPresent()){
            return retrievedNewsletter.get();
        } else {
            throw new NewsletterNotFoundException();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    private boolean checkSubscriptionIdNotDuplicated(SubscriptionEntity subscriptionEntity) throws SubscriptionIdDuplicateException {
        Optional<SubscriptionEntity> retrievedSubscription = subscriptionRepository.findById(subscriptionEntity.getSubscriptionId());
        if ( retrievedSubscription.isPresent()){
            throw new SubscriptionIdDuplicateException();
        } else {
            return true;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    private boolean checkUserEmailAndNewsletterIdNotDuplicated(SubscriptionEntity subscriptionEntity) throws UserEmailAndNewsletterIdDuplicateException {

        Optional<SubscriptionEntity> retrievedSubscription = subscriptionRepository.findByUserAndNewsletter(
                subscriptionEntity.getUserEntity(),
                subscriptionEntity.getNewsletterEntity());
        if (retrievedSubscription.isPresent()) {
            throw new UserEmailAndNewsletterIdDuplicateException();
        } else {
            return true;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Subscription findSubscription(String subscriptionId) {
        Optional<SubscriptionEntity> retrievedSubscription = subscriptionRepository.findById(subscriptionId);
        return (retrievedSubscription.isPresent())
            ? retrievedSubscription.get().toSubcription()
            : null;
    }

}
