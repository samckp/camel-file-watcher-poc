package com.sam.filewatch;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class MailProcessor implements Processor {

    @Value("${mailFrom}")
    private String mailFrom;

    @Value("${mailTo}")
    private String mailTo;

    @Value("${zipFilePath}")
    private String zipFilePath;

    @Autowired
    JavaMailSender emailSender;

    @Override
    public void process(Exchange exchange) throws Exception {

        String messageBody ="Camel Test Message !! \n Please find attached file-->";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(mailFrom);
        helper.setTo(mailTo);
        helper.setSubject("Message from Camel Route");
        helper.setText(messageBody + exchange.getIn().getHeader(Exchange.FILE_NAME));

        FileSystemResource file = new FileSystemResource(zipFilePath + exchange.getIn().getHeader(Exchange.FILE_NAME));
        helper.addAttachment(file.getFilename(), file);
        emailSender.send(message);
    }
}
