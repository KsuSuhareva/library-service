package ru.itq.library_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LibraryListener {

    @KafkaListener(
            topics = "${listen.record.topic}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(String jsonRecord) {

    }
}
