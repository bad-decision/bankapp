package ru.azmeev.bank.cash.services;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import ru.azmeev.bank.cash.IntegrationTestBase;
import ru.azmeev.bank.cash.service.NotificationService;
import ru.azmeev.bank.cash.web.dto.CashActionResult;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@EmbeddedKafka(
        topics = {"cash-topic"}
)
public class NotificationServiceIT extends IntegrationTestBase {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    void sendNotification_correctKafkaMsg() {
        CashActionResult cashActionResult = CashActionResult.builder()
                .message("Success")
                .success(true)
                .id(UUID.randomUUID())
                .build();

        try (JsonDeserializer<CashActionResult> deserializer = new JsonDeserializer<>(CashActionResult.class);
             var consumerForTest = new DefaultKafkaConsumerFactory<>(
                KafkaTestUtils.consumerProps("notification", "true", embeddedKafkaBroker),
                new StringDeserializer(),
                deserializer
        ).createConsumer()) {
            deserializer.addTrustedPackages("*");
            consumerForTest.subscribe(List.of("cash-topic"));
            notificationService.notify(cashActionResult);
            var consumerRecord = KafkaTestUtils.getSingleRecord(consumerForTest, "cash-topic", Duration.ofSeconds(5));
            assertThat(consumerRecord.key()).isEqualTo(cashActionResult.getId().toString());
            assertThat(consumerRecord.value().getMessage()).isEqualTo(cashActionResult.getMessage());
            assertThat(consumerRecord.value().getSuccess()).isEqualTo(cashActionResult.getSuccess());
        }
    }
}