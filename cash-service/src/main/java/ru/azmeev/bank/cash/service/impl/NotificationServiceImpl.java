package ru.azmeev.bank.cash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.cash.service.NotificationService;
import ru.azmeev.bank.cash.web.dto.CashActionResult;

@Service
@RequiredArgsConstructor
@Primary
public class NotificationServiceImpl implements NotificationService {
    @Value("${kafka.topics.cash-notification}")
    private String notificationTopic;
    private final KafkaTemplate<String, CashActionResult> kafkaTemplate;

    @Override
    public void notify(CashActionResult cashActionResult) {
        kafkaTemplate.send(notificationTopic, cashActionResult.getId().toString(), cashActionResult);
    }
}
