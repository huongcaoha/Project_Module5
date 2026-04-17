package com.ra.module5_project.service.sendMail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Properties;
@Component
public class ActiveAccount {

    @Autowired
    private JavaMailSender mailSender;

    public  void sendCode(String email ,String subject , long code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("huongcaoha1995@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText("http://localhost:8080/api.myService.com/v1/auth/active-account/"+email+"/verify/"+code);

        mailSender.send(message);
        System.out.println("Email đã được gửi thành công đến: " + email);
    }
}
