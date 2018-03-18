//package com.codingchallenge.subscriptionsystem.common.model.rest.resources;
//
//import com.codingchallenge.subscriptionsystem.common.model.ISubscription;
//import com.codingchallenge.subscriptionsystem.common.model.IUser;
//import com.codingchallenge.subscriptionsystem.common.model.Newsletter;
//import com.codingchallenge.subscriptionsystem.common.model.User;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.hateoas.ResourceSupport;
//
//import java.io.Serializable;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class SubscriptionResource extends ResourceSupport implements Serializable, ISubscription {
//
//    private String subscriptionId;
//    private User user;
//    private Newsletter newsletter;
//}
