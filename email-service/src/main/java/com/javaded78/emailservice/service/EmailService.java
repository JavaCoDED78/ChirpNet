package com.javaded78.emailservice.service;


import com.javaded78.commons.event.auth.SendRegistrationCodeEmailEvent;

public interface EmailService {

    void sendEmail(SendRegistrationCodeEmailEvent sendRegistrationCodeEmailEvent);
}
