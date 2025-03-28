package ru.itq.library_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookRecord {
    private String userLogin;
    private String userFullName;
    private String userEmail;
    private boolean borrowAllowed;
    private String bookTitle;
    private String bookAuthor;
    private LocalDateTime bookPublishedDate;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;
}
