package ru.itq.library_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.itq.library_service.dto.BookRecord;
import ru.itq.library_service.service.AccountingBookService;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class LibraryKafkaListener {
    private final AccountingBookService accountingBookService;

    @KafkaListener(
            topics = "${listen.record.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenBatchRecord(List<BookRecord> records) {
        accountingBookService.saveOrUpdateBatch(records);
    }
}
