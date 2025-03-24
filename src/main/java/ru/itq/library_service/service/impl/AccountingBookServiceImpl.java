package ru.itq.library_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itq.library_service.dto.BookRecord;
import ru.itq.library_service.model.entity.AccountingBook;
import ru.itq.library_service.repository.AccountingBookRepository;
import ru.itq.library_service.service.AccountingBookService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountingBookServiceImpl implements AccountingBookService {
    private static final Integer ALLOWED_PERIOD_DAYS = 20;
    private final AccountingBookRepository accountingBookRepository;

    @Override
    public List<AccountingBook> findOverdueBooks() {
        return accountingBookRepository.findByReturnedDateIsNullAndBorrowedDateBefore(LocalDate.now().minusDays(ALLOWED_PERIOD_DAYS));
    }

    @Override
    public void loadBookRecords(BookRecord record) {
        //сохранить
    }
}
