package ru.itq.library_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itq.library_service.dto.BookRecord;
import ru.itq.library_service.service.RedisQueueService;

import java.util.List;

@RestController
@RequestMapping("/accountingBooks")
@RequiredArgsConstructor
@Validated
public class RedisQueueController {

    private final RedisQueueService redisQueueService;

    @PostMapping("/pushToQueue")
    public ResponseEntity<HttpStatus> pushBookRecordsToQueue(@RequestBody List<BookRecord> records) {
        redisQueueService.pushToQueue(records);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
