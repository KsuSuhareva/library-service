package ru.itq.library_service.service;

import ru.itq.library_service.model.entity.Subscription;

public interface SubscriptionService {
    Subscription findByUserFullName(String userFullName);
}
