package ru.itq.library_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itq.library_service.dto.BookRecord;
import ru.itq.library_service.service.AccountingBookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountingBookController {

    private final AccountingBookService accountingBookService;

    @PostMapping("/pushToQueue")
    public ResponseEntity<HttpStatus> pushBookRecordsToQueue(@RequestBody List<BookRecord> records) {
        accountingBookService.publishToQueue(records);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
