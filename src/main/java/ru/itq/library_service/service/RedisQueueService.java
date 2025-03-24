package ru.itq.library_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.itq.library_service.dto.BookRecord;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisQueueService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String QUEUE_NAME = "bookRecordQueue";
    private final RedisTemplate<String, String> redisTemplate;
    private final AccountingBookService accountingBookService;


    public void pushToQueue(List<BookRecord> records) {
        records.forEach(record -> {
            try {
                redisTemplate.opsForList().rightPush(QUEUE_NAME, objectMapper.writeValueAsString(record));
            } catch (JsonProcessingException e) {
                log.error("Данные для загрузки не корректны");
                throw new IllegalArgumentException("Данные для загрузки не корректны");
            }
        });
    }

    @Async
    @Scheduled(fixedDelay = 5000)
    public void popFromQueue() {
        String jsonRecords = redisTemplate.opsForList().leftPop(QUEUE_NAME);
        try {
            if (jsonRecords == null) {
                return;
            }
            BookRecord bookRecord = objectMapper.readValue(jsonRecords, BookRecord.class);
            accountingBookService.loadBookRecords(bookRecord);
            log.info("Запись сохранена успешно {} : ", bookRecord);
        } catch (JsonProcessingException e) {
            log.error("Данные для загрузки не корректны: " + jsonRecords);
        }
    }
}
