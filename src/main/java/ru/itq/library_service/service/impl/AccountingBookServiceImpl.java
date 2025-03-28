package ru.itq.library_service.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itq.library_service.config.LibraryProperties;
import ru.itq.library_service.dto.BookRecord;
import ru.itq.library_service.kafka.LibraryKafkaPublisher;
import ru.itq.library_service.model.entity.AccountingBook;
import ru.itq.library_service.model.entity.Book;
import ru.itq.library_service.model.entity.Subscription;
import ru.itq.library_service.repository.AccountingBookRepository;
import ru.itq.library_service.repository.BookRepository;
import ru.itq.library_service.repository.SubscriptionRepository;
import ru.itq.library_service.service.AccountingBookService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountingBookServiceImpl implements AccountingBookService {
    private static final Integer ALLOWED_PERIOD_DAYS = 20;
    private final LibraryProperties properties;
    private final SubscriptionRepository subscriptionRepository;
    private final BookRepository bookRepository;
    private final AccountingBookRepository accountingBookRepository;
    private final LibraryKafkaPublisher libraryPublisher;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AccountingBook> findOverdueBooks() {
        return accountingBookRepository.findByReturnedDateIsNullAndBorrowedDateBefore(LocalDate.now().minusDays(ALLOWED_PERIOD_DAYS));
    }

    @Override
    public void publishToQueue(List<BookRecord> records) {
        records.parallelStream()
                .forEach(record -> libraryPublisher.publish(record, properties.getPublishRecordTopic()));
    }

    @Override
    @Transactional
    public void saveOrUpdate(List<BookRecord> records) {
        for (int i = 0; i < records.size(); i++) {
            entityManager.merge(records.get(i));

            if (i % 50 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}
