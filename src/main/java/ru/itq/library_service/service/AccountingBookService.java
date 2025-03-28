package ru.itq.library_service.service;

import ru.itq.library_service.dto.BookRecord;
import ru.itq.library_service.model.entity.AccountingBook;

import java.util.List;

public interface AccountingBookService {
    List<AccountingBook> findOverdueBooks();

    void publishToQueue(List<BookRecord> records);

    void saveOrUpdate(List<BookRecord> records);
}
