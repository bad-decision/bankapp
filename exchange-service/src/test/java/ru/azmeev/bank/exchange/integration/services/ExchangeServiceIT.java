package ru.azmeev.bank.exchange.integration.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import ru.azmeev.bank.exchange.integration.IntegrationTestBase;
import ru.azmeev.bank.exchange.service.ExchangeService;
import ru.azmeev.bank.exchange.web.dto.ExchangeRateDto;
import ru.azmeev.bank.exchange.web.dto.ExchangeRateList;

import java.math.BigDecimal;
import java.util.List;

@EmbeddedKafka(
        topics = {"exchange-messages"}
)
public class ExchangeServiceIT extends IntegrationTestBase {

    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private KafkaTemplate<String, ExchangeRateList> kafkaTemplate;

    @Test
    void sendExchangeRate_correctKafkaMsg() {
        List<ExchangeRateDto> rates = exchangeService.getRates();
        Assertions.assertEquals(0, rates.size());
        ExchangeRateDto rateDto = ExchangeRateDto.builder()
                .currency("USD")
                .value(BigDecimal.valueOf(100))
                .build();
        List<ExchangeRateDto> rateDtos = List.of(rateDto);
        ExchangeRateList rateList = ExchangeRateList.builder()
                .rates(rateDtos)
                .build();

        kafkaTemplate.send("exchange-messages", "ExchangeRates", rateList);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        rates = exchangeService.getRates();
        Assertions.assertEquals(1, rates.size());
        Assertions.assertEquals(rateDto.getCurrency(), rates.get(0).getCurrency());
        Assertions.assertEquals(rateDto.getValue(), rates.get(0).getValue());
    }
}
