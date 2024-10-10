package com.javaded78.emailservice.service;

import com.javaded78.emailservice.event.SendEmailEvent;

public interface EmailService {

    void sendEmail(SendEmailEvent sendEmailEvent);
}
