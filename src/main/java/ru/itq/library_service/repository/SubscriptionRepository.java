package ru.itq.library_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itq.library_service.model.entity.Subscription;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("SELECT s FROM Subscription s LEFT JOIN FETCH s.books WHERE s.userFullName = :userFullName")
    Optional<Subscription> findByUserFullName(@Param("userFullName") String userFullName);
}
