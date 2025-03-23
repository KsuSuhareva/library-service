package ru.itq.library_service.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.itq.library_service.model.entity.Subscription;

@Component
@RequiredArgsConstructor
public class EmailSender implements NotificationSender {
    private final JavaMailSender mailSender;

    @Override
    public void send(Subscription subscription) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(subscription.getUserEmail());
        message.setSubject("Напоминание: Верните книгу!");
        message.setText("Вы держите книгу более 20 дней. Пожалуйста, верните её в библиотеку.");
        mailSender.send(message);
    }
}
