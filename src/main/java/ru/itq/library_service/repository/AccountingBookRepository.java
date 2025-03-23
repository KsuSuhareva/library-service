package ru.itq.library_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itq.library_service.model.entity.AccountingBook;

import java.time.LocalDate;
import java.util.List;

public interface AccountingBookRepository extends JpaRepository<AccountingBook, Long> {
    List<AccountingBook> findByReturnedDateIsNullAndBorrowedDateBefore(LocalDate date);
}
