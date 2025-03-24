package ru.itq.library_service.sender;

import ru.itq.library_service.model.entity.Subscription;

public interface NotificationSender {
    void send(Message message);
}
