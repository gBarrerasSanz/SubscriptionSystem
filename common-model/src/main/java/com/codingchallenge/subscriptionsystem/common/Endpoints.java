package com.codingchallenge.subscriptionsystem.common;

/**
 * Defines the service endpoints
 */
public class Endpoints {

    public static final String API_CREATE_SUBSCRIPTION = "http://localhost:8080/api/subscriptions";
    public static final String SMS_CREATE_SUBSCRIPTION = "http://subscriptionmanagerservice:8080/subscriptionmanagerservice/createSubscription";
    public static final String PS_CREATE_SUBSCRIPTION = "http://persistenceservice:8080/persistenceservice/subscriptions";
    public static final String EVS_REGISTER_SUBSCRIPTION = "http://eventservice:8080/eventservice/registerSubscription";
    public static final String EMS_SEND_EMAIL_NEWSLETTER = "http://emailservice:8080/eventservice/sendEmailNewsletterNotification";

//    public static final String API_CREATE_SUBSCRIPTION = "http://localhost:8080/api/subscriptions";
//    public static final String SMS_CREATE_SUBSCRIPTION = "http://localhost:8081/subscriptionmanagerservice/createSubscription";
//    public static final String PS_CREATE_SUBSCRIPTION = "http://localhost:8082/persistenceservice/subscriptions";
//    public static final String EVS_REGISTER_SUBSCRIPTION = "http://localhost:8083/eventservice/registerSubscription";
//    public static final String EMS_SEND_EMAIL_NEWSLETTER = "http://localhost:8084/eventservice/sendEmailNewsletterNotification";
}
