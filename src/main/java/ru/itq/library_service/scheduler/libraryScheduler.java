package ru.itq.library_service.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.itq.library_service.model.entity.AccountingBook;
import ru.itq.library_service.model.entity.Subscription;
import ru.itq.library_service.sender.NotificationSender;
import ru.itq.library_service.service.AccountingBookService;
import ru.itq.library_service.service.SubscriptionService;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class libraryScheduler {
    private final AccountingBookService accountingBookService;
    private final NotificationSender sender;

    @Scheduled(cron = "${notifications.cron}")
    public void checkOverdueBooks() {
        List<AccountingBook> accountingBooks = accountingBookService.findOverdueBooks();

        accountingBooks.stream()
                .map(AccountingBook::getSubscription)
                .toList()
                .forEach(sender::send);
    }
}
