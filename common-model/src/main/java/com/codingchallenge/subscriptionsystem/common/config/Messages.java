package com.codingchallenge.subscriptionsystem.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Messages {

    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;


    public String get(String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }
}
