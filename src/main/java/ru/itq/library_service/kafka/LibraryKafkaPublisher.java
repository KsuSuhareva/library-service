package ru.itq.library_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.itq.library_service.config.LibraryProperties;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class LibraryKafkaPublisher {
    private final LibraryProperties properties;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void publish(Object message, String topicName) {
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(
                    topicName,
                    UUID.randomUUID().toString(),
                    getJson(message)
            );
            record.headers().add(new RecordHeader("service-name", getBytes(properties.getServiceName())));
            kafkaTemplate.send(record);
        } catch (Exception exception) {
            log.error("Error sending: ", exception);
            throw exception;
        }
    }

    public String getJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            log.error("Serialization error: ", exception);
            throw new SerializationException("Serialization error", exception);
        }
    }

    private byte[] getBytes(String serviceName) {
        return serviceName == null
                ? new byte[0]
                : serviceName.getBytes(StandardCharsets.UTF_8);
    }
}
