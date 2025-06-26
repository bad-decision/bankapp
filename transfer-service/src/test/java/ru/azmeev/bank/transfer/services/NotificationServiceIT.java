package ru.azmeev.bank.transfer.services;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import ru.azmeev.bank.transfer.IntegrationTestBase;
import ru.azmeev.bank.transfer.service.NotificationService;
import ru.azmeev.bank.transfer.web.dto.TransferResultDto;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@EmbeddedKafka(
        topics = {"transfer.notification.msg"}
)
public class NotificationServiceIT extends IntegrationTestBase {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    void sendNotification_correctKafkaMsg() {
        TransferResultDto resultDto = TransferResultDto.builder()
                .message("Success")
                .success(true)
                .id(UUID.randomUUID())
                .build();

        try (JsonDeserializer<TransferResultDto> deserializer = new JsonDeserializer<>(TransferResultDto.class);
             var consumerForTest = new DefaultKafkaConsumerFactory<>(
                KafkaTestUtils.consumerProps("notification", "true", embeddedKafkaBroker),
                new StringDeserializer(),
                deserializer
        ).createConsumer()) {
            deserializer.addTrustedPackages("*");
            consumerForTest.subscribe(List.of("transfer.notification.msg"));
            notificationService.notify(resultDto);
            var consumerRecord = KafkaTestUtils.getSingleRecord(consumerForTest, "transfer.notification.msg", Duration.ofSeconds(5));
            assertThat(consumerRecord.key()).isEqualTo(resultDto.getId().toString());
            assertThat(consumerRecord.value().getMessage()).isEqualTo(resultDto.getMessage());
            assertThat(consumerRecord.value().getSuccess()).isEqualTo(resultDto.getSuccess());
        }
    }
}