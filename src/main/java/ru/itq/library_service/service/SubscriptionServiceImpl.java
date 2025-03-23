package ru.itq.library_service.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itq.library_service.model.entity.Subscription;
import ru.itq.library_service.repository.SubscriptionRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription findByFullName(String userFullName) {
        Subscription subscription = subscriptionRepository.findByUserFullName(userFullName)
                .orElseThrow(() -> new EntityNotFoundException("Абонемент не найден по ФИО: " + userFullName));
        return subscription;
    }
}
