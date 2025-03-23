package ru.itq.library_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itq.library_service.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
