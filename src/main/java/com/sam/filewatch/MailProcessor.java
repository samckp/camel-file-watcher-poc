package com.sam.filewatch;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailProcessor implements Processor {

    @Value("mailFrom")
    private String mailFrom;

    @Value("mailTo")
    private String mailTo;

    @Autowired
    JavaMailSender emailSender;

    @Override
    public void process(Exchange exchange) throws Exception {

        String messageBody ="Camel Test Message !!";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(mailTo);
        message.setSubject("Message from Camel Route");
        message.setText(messageBody);

        emailSender.send(message);

    }
}
