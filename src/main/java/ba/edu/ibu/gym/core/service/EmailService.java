package ba.edu.ibu.gym.core.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(String to, String name, String subject, String body, String altBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tesingtesting4@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);


        try {
            emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

