package ru.azmeev.bank.transfer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.transfer.service.NotificationService;
import ru.azmeev.bank.transfer.web.dto.TransferResultDto;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    @Value("${kafka.topics.transfer-notification}")
    private String notificationTopic;
    private final KafkaTemplate<String, TransferResultDto> kafkaTemplate;

    @Override
    public void notify(TransferResultDto dto) {
        kafkaTemplate.send(notificationTopic, dto.getId().toString(), dto);
    }
}
