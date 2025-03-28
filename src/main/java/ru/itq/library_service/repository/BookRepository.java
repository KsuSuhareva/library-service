package ru.itq.library_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itq.library_service.model.entity.Book;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleAndAuthorAndPublishedDate(String title, String author, LocalDateTime publishedDate);
}
