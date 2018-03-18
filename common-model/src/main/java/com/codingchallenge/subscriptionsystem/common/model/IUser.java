package com.codingchallenge.subscriptionsystem.common.model;

import java.util.Date;

public interface IUser {

    public enum Gender{
        MALE, FEMALE
    }

    String getEmail();
    Date getBirthDate();
    Boolean getConsent();
    String getFirstName();
    User.Gender getGender();
}
