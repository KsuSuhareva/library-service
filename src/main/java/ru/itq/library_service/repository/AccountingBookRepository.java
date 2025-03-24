package ru.itq.library_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itq.library_service.model.entity.AccountingBook;

import java.time.LocalDate;
import java.util.List;

public interface AccountingBookRepository extends JpaRepository<AccountingBook, Long> {
    @Query("SELECT ab FROM AccountingBook ab JOIN FETCH ab.subscription JOIN FETCH ab.book WHERE ab.returnedDate IS NULL AND ab.borrowedDate < :date")
    List<AccountingBook> findByReturnedDateIsNullAndBorrowedDateBefore(@Param("date") LocalDate date);
}
