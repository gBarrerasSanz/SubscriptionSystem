package com.codingchallenge.subscriptionsystem.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsletterNotificationEvent {

    private NewsletterNotification newsletterNotification;
    private Date dateToPublish;
}
