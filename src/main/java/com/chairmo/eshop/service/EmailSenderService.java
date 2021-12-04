package com.chairmo.eshop.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.chairmo.eshop.config.AppProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final AppProperties appProperties;

    public void sendMail(String userEmail, String confirmationToken){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Account Activation!");
        mailMessage.setText("To confirm your account, please click here : "
                + appProperties.getAppUrl() + "/verify?token="+ confirmationToken
//                +"http://localhost:8090/api/auth/confirm-account?token="+ confirmationToken
                + "   Note: This link will expire after 10 minutes.");
        javaMailSender.send(mailMessage);
    }

    public void sendPasswordResetLink(String userEmail, String confirmationToken){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Change Password!");
        mailMessage.setText("To change your account password, please click here : "
                + appProperties.getAppUrl() + "/changePassword?token="+ confirmationToken + "&email=" + userEmail
//                +"http://localhost:8090/api/auth/reset-password?token="+ confirmationToken
                + "   Note: This link will expire after 10 minutes.");
        javaMailSender.send(mailMessage);
    }

    public boolean sendSimpleMail(String to, String sub, String body){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("noreply@eshop.io");
        mailMessage.setTo(to);
        mailMessage.setSubject(sub);
        mailMessage.setText(body);
        boolean isSent = false;
        try
        {
            javaMailSender.send(mailMessage);
            isSent = true;
        }
        catch (Exception e) {}

        return isSent;
    }
}
