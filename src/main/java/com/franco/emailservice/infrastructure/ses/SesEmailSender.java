package com.franco.emailservice.infrastructure.ses;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.franco.emailservice.adapters.EmailSenderGateway;
import com.franco.emailservice.core.ex.EmailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SesEmailSender implements EmailSenderGateway {

    private final AmazonSimpleEmailService awsEmailService;

    @Autowired
    public SesEmailSender(AmazonSimpleEmailService awsEmailService){
        this.awsEmailService = awsEmailService;
    }


    @Override
    public void sendEmail(String to, String subject, String body) {
        SendEmailRequest request = new SendEmailRequest()
                .withSource("lucasfrancodb0@gmail.com")
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withSubject(new Content(subject))
                        .withBody(new Body().withText(new Content(body)))
                );

        try{
            this.awsEmailService.sendEmail(request);
        } catch (AmazonServiceException ex){
            throw new EmailServiceException("Falha ao enviar email", ex);
        }

    }
}
