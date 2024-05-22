package com.eventec.eventec.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendValidationEmail(String to, String validationCode) {
        String confirmationLink = "http://localhost:8080/api/users/confirmEmail?email=" + URLEncoder.encode(to, StandardCharsets.UTF_8) + "&code=" + URLEncoder.encode(validationCode, StandardCharsets.UTF_8);
        String emailBody = "Por favor, clique no link a seguir para validar seu e-mail: " + confirmationLink;

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(to);
            helper.setSubject("Confirmação de E-mail");
            helper.setText(emailBody, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }
    }
}

