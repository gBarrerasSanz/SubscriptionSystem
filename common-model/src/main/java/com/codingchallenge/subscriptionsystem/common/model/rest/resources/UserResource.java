//package com.codingchallenge.subscriptionsystem.common.model.rest.resources;
//
//import com.codingchallenge.subscriptionsystem.common.model.IUser;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonView;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.hateoas.ResourceSupport;
//
//import java.io.Serializable;
//import java.util.Date;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class UserResource extends ResourceSupport implements Serializable, IUser {
//
//    private String email;
//    private Date birthDate;
//    private String firstName;
//    private Gender gender;
//}
