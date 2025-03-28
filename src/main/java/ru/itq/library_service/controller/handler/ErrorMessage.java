package ru.itq.library_service.controller.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ErrorMessage {
    private String errorMessage;
    private HttpStatus httpStatus;
    private LocalDateTime errorDate;

    public ErrorMessage(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
        this.errorDate = LocalDateTime.now();

    }
}
