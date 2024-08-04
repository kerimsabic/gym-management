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
        // SimpleMailMessage does not support HTML, so `altBody` is not used here.

        try {
            emailSender.send(message);
            // You may add logging or other handling here.
        } catch (Exception e) {
            // Handle exception or log error
            e.printStackTrace();
        }
    }
}

