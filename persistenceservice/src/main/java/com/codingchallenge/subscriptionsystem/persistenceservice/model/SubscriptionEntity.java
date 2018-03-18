package com.codingchallenge.subscriptionsystem.persistenceservice.model;

import com.codingchallenge.subscriptionsystem.common.model.ISubscription;
import com.codingchallenge.subscriptionsystem.common.model.Newsletter;
import com.codingchallenge.subscriptionsystem.common.model.Subscription;
import com.codingchallenge.subscriptionsystem.common.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "subscription", uniqueConstraints={@UniqueConstraint(columnNames = {"userEmail", "newsletterId"})})
public class SubscriptionEntity implements ISubscription {

    @Id
    @Column(name = "subscriptionId")
    private String subscriptionId;

    @JoinColumn(name = "userEmail")
    @ManyToOne(targetEntity=UserEntity.class, fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private UserEntity user;

    @JoinColumn(name = "newsletterId")
    @ManyToOne(targetEntity=NewsletterEntity.class, fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private NewsletterEntity newsletter;

    public SubscriptionEntity(Subscription subscription) {
        this.subscriptionId = subscription.getSubscriptionId();
        this.user = new UserEntity(subscription.getUser());
        this.newsletter = new NewsletterEntity(subscription.getNewsletter());
    }

    public Subscription toSubcription(){
        return new Subscription(this.subscriptionId, this.user.toUser(), this.newsletter.toNewsletter());
    }

    @Override
    public String getSubscriptionId() {
        return this.subscriptionId;
    }

    @Override
    public User getUser() {
        return this.user.toUser();
    }

    @Override
    public Newsletter getNewsletter() {
        return this.newsletter.toNewsletter();
    }

    public UserEntity getUserEntity(){
        return this.user;
    }

    public NewsletterEntity getNewsletterEntity(){
        return this.newsletter;
    }
}
