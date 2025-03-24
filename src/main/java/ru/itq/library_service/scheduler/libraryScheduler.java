package ru.itq.library_service.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.itq.library_service.model.entity.AccountingBook;
import ru.itq.library_service.sender.Message;
import ru.itq.library_service.sender.NotificationSender;
import ru.itq.library_service.service.AccountingBookService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class libraryScheduler {
    private final AccountingBookService accountingBookService;
    private final NotificationSender sender;

    @Scheduled(initialDelay = 0, fixedRateString = "${scheduler.notification.interval}")
    public void checkOverdueBooks() {
        List<AccountingBook> accountingBooks = accountingBookService.findOverdueBooks();

        List<String> emails = accountingBooks.stream()
                .map(accountingBook -> accountingBook.getSubscription().getUserEmail())
                .toList();
        Message message = new Message();
        message.setTitle("Напоминание: Верните книгу!");
        message.setText("Вы держите книгу более 20 дней. Пожалуйста, верните её в библиотеку.");
        message.setRecipientContacts(emails);
        sender.send(message);
    }
}
