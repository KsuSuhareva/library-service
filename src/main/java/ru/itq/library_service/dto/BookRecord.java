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
    @NotNull
    private String userLogin;
    @NotNull
    private String userFullName;
    @NotNull
    private String userEmail;
    private boolean borrowAllowed;
    @NotNull
    private String bookTitle;
    @NotNull
    private String bookAuthor;
    @NotNull
    private LocalDateTime bookPublishedDate;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;
}
