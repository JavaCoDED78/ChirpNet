package com.javaded78.emailservice.service;


import com.javaded78.commons.event.SendEmailEvent;

public interface EmailService {

    void sendEmail(SendEmailEvent sendEmailEvent);
}
