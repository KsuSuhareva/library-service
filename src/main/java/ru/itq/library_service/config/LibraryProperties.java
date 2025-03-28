package ru.itq.library_service.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class LibraryProperties {
    @Value("${spring.application.name}")
    private String serviceName;
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;
    @Value("${publish.record.topic}")
    private String publishRecordTopic;
    @Value("${listen.record.topic}")
    private String listenRecordTopic;
}
