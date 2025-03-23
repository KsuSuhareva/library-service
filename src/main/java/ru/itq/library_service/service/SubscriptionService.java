package ru.itq.library_service.service;

import ru.itq.library_service.model.entity.Subscription;

import java.util.List;

public interface SubscriptionService {
    Subscription findByFullName(String userFullName);
}
