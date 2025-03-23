package ru.itq.library_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itq.library_service.model.entity.Subscription;
import ru.itq.library_service.service.SubscriptionService;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private static final String FIO_PATTERN = "^[A-Za-zА-Яа-яЁё]+\\s[A-Za-zА-Яа-яЁё]+\\s[A-Za-zА-Яа-яЁё]+$";
    private final SubscriptionService subscriptionService;

    @GetMapping("/by-user-full-name")
    public ResponseEntity<Subscription> getByUserFullName(@RequestParam @Valid @Pattern(regexp = FIO_PATTERN) String userFullName) {
        return new ResponseEntity<>(subscriptionService.findByFullName(userFullName), HttpStatus.OK);
    }
}
