package ru.azmeev.bank.notification.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.notification.service.NotificationService;
import ru.azmeev.bank.notification.dto.CashActionResult;
import ru.azmeev.bank.notification.dto.TransferActionResult;
import org.springframework.kafka.retrytopic.DltStrategy;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Override
    @RetryableTopic(
            attempts = "2",
            backoff = @Backoff(delay = 1_000, multiplier = 2, maxDelay = 8_000),
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(topics = "${kafka.topics.cash-notification}")
    public void cashOperationNotification(CashActionResult dto) {
        System.out.println(dto.getMessage());
    }

    @Override
    @KafkaListener(topics = "${kafka.topics.transfer-notification}")
    @RetryableTopic(
            attempts = "2",
            backoff = @Backoff(delay = 1_000, multiplier = 2, maxDelay = 8_000),
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    public void transferOperationNotification(TransferActionResult dto) {
        System.out.println(dto.getMessage());
    }

    @DltHandler
    public void handleDltMessage(ConsumerRecord<?, ?> record) {
        log.info("Message landed in DLT: " + record.value());
    }
}
