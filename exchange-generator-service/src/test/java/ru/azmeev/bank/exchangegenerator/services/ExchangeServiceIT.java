package ru.azmeev.bank.exchangegenerator.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import ru.azmeev.bank.exchangegenerator.IntegrationTestBase;
import ru.azmeev.bank.exchangegenerator.dto.ExchangeRateList;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@EmbeddedKafka(
        topics = {"exchange-messages"}
)
public class ExchangeServiceIT extends IntegrationTestBase {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    void sendExchangeRate_correctKafkaMsg() {
        try (JsonDeserializer<ExchangeRateList> deserializer = new JsonDeserializer<>(ExchangeRateList.class);
             var consumerForTest = new DefaultKafkaConsumerFactory<>(
                KafkaTestUtils.consumerProps("exchange-rates", "true", embeddedKafkaBroker),
                new StringDeserializer(),
                deserializer
        ).createConsumer()) {
            deserializer.addTrustedPackages("*");
            consumerForTest.subscribe(List.of("exchange-messages"));
            var consumerRecords = KafkaTestUtils.getRecords(consumerForTest, Duration.ofSeconds(5));
            ConsumerRecord<String, ExchangeRateList> consumerRecord = consumerRecords.records("exchange-messages").iterator().next();
            assertThat(consumerRecord.key()).isEqualTo("EXCHANGE-RATES");
            assertThat(consumerRecord.value().getRates().size()).isEqualTo(3);
            assertThat(consumerRecord.value().getRates().get(0).getCurrency()).isEqualTo("USD");
        }
    }
}