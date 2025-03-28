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
import ru.itq.library_service.service.AccountingBookService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountingBookServiceImpl implements AccountingBookService {
    private static final Integer ALLOWED_PERIOD_DAYS = 20;
    private final LibraryProperties properties;
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
    public void saveOrUpdateBatch(List<BookRecord> records) {
        Map<String, Book> bookCache = new HashMap<>();
        Map<String, Subscription> subscriptionCache = new HashMap<>();

        for (BookRecord record : records) {
            Book book = findBookCacheOrBase(record, bookCache);
            Subscription subscription = findSubscriptionCacheOrBase(record, book, subscriptionCache);
            saveAccountingBook(book, subscription, record);
        }
        entityManager.flush();
        entityManager.clear();

        bookCache.clear();
        subscriptionCache.clear();
    }

    private Book findBookCacheOrBase(BookRecord record, Map<String, Book> bookCache) {
        return bookCache.computeIfAbsent(record.getBookTitle(), title -> {
            return entityManager.createQuery(
                            "SELECT b FROM Book b WHERE b.title = :title AND b.author = :author AND b.publishedDate = :publishedDate",
                            Book.class)
                    .setParameter("title", record.getBookTitle())
                    .setParameter("author", record.getBookAuthor())
                    .setParameter("publishedDate", record.getBookPublishedDate())
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElseGet(() -> {
                        Book newBook = new Book(record.getBookTitle(), record.getBookAuthor(), record.getBookPublishedDate());
                        entityManager.persist(newBook);
                        return newBook;
                    });
        });
    }


    private Subscription findSubscriptionCacheOrBase(BookRecord record, Book newBook, Map<String, Subscription> subscriptionCache) {
        return subscriptionCache.computeIfAbsent(record.getUserFullName(), login -> {
            return entityManager.createQuery(
                            "SELECT s FROM Subscription s WHERE s.userFullName = :fullName",
                            Subscription.class)
                    .setParameter("fullName", record.getUserFullName())
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElseGet(() -> {
                        Subscription newSubscription = new Subscription(login, record.getUserFullName(), record.getUserEmail(), record.isBorrowAllowed());
                        List<Book> books = newSubscription.getBooks();
                        if (!books.contains(newBook)) {
                            books.add(newBook);
                            newSubscription.setBooks(books);
                        }
                        entityManager.persist(newSubscription);
                        return newSubscription;
                    });
        });
    }

    private void saveAccountingBook(Book book, Subscription subscription, BookRecord record) {
        AccountingBook accountingBook = new AccountingBook(subscription, book, record.getBorrowedDate(), record.getReturnedDate());
        entityManager.persist(accountingBook);
    }
}
