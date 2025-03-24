package ru.itq.library_service.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender implements NotificationSender {
    @Value("${mail.from}")
    private String emailFrom;
    private final JavaMailSender mailSender;

    @Override
    public void send(Message message) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(message.getRecipientContactArray());
        simpleMessage.setFrom(emailFrom);
        simpleMessage.setSubject(message.getTitle());
        simpleMessage.setText(message.getText());
        mailSender.send(simpleMessage);
    }
}
