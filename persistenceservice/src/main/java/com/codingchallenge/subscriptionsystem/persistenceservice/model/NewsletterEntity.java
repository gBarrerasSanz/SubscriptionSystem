package com.codingchallenge.subscriptionsystem.persistenceservice.model;

import com.codingchallenge.subscriptionsystem.common.model.INewsletter;
import com.codingchallenge.subscriptionsystem.common.model.Newsletter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "newsletter")
public class NewsletterEntity implements INewsletter {

    @Id
    @Column(name ="newsletterId")
    private String newsletterId;
    @Column(name ="newsletterName")
    private String newsletterName;

    public NewsletterEntity(Newsletter newsletter) {
        this.newsletterId = newsletter.getNewsletterId();
        this.newsletterName = newsletter.getNewsletterName();
    }

    public Newsletter toNewsletter(){
        return new Newsletter(newsletterId, newsletterName);
    }
}
