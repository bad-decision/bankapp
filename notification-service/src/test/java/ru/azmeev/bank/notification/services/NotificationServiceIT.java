package ru.azmeev.bank.notification.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import ru.azmeev.bank.notification.IntegrationTestBase;
import ru.azmeev.bank.notification.dto.CashActionResult;
import ru.azmeev.bank.notification.dto.TransferActionResult;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@EmbeddedKafka(
        topics = {"cash.notification.msg", "transfer.notification.msg"}
)
public class NotificationServiceIT extends IntegrationTestBase {
    @Autowired
    private KafkaTemplate<String, CashActionResult> cashKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, TransferActionResult> transferKafkaTemplate;

    @Test
    void getCashNotification_printCorrectMsg() throws InterruptedException {
        CashActionResult cashActionResult = CashActionResult.builder()
                .id(UUID.randomUUID())
                .message("Success kafka message")
                .success(true)
                .build();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        cashKafkaTemplate.send("cash.notification.msg", cashActionResult.getId().toString(), cashActionResult);

        Thread.sleep(2000);
        assertThat(outContent.toString()).contains(cashActionResult.getMessage());
    }

    @Test
    void getTransferNotification_printCorrectMsg() throws InterruptedException {
        TransferActionResult transferActionResult = TransferActionResult.builder()
                .id(UUID.randomUUID())
                .message("Success kafka message")
                .success(true)
                .build();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        transferKafkaTemplate.send("transfer.notification.msg", transferActionResult.getId().toString(), transferActionResult);

        Thread.sleep(2000);
        assertThat(outContent.toString()).contains(transferActionResult.getMessage());
    }
}