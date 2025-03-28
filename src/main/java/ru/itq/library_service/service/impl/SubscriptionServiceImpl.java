package ru.itq.library_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itq.library_service.model.entity.Subscription;
import ru.itq.library_service.repository.SubscriptionRepository;
import ru.itq.library_service.service.SubscriptionService;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription findByUserFullName(String userFullName) {
        return subscriptionRepository.findByUserFullName(userFullName)
                .orElseThrow(() -> new EntityNotFoundException("Абонемент не найден по ФИО: " + userFullName));
    }
}
