package ru.itq.library_service.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id", nullable = false)
    private Long id;

    @Column(name = "user_login", nullable = false)
    private String userLogin;

    @Column(name = "user_full_name", nullable = false)
    private String userFullName;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "borrow_allowed", nullable = false)
    private boolean borrowAllowed;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private List<Book> books;

    public Subscription(String userLogin, String userFullName, String userEmail, boolean borrowAllowed) {
        this.userLogin = userLogin;
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.borrowAllowed = borrowAllowed;
    }
}
