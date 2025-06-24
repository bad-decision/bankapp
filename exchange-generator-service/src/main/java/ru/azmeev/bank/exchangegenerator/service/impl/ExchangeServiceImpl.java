package ru.azmeev.bank.exchangegenerator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.exchangegenerator.dto.ExchangeRateList;
import ru.azmeev.bank.exchangegenerator.service.ExchangeService;
import ru.azmeev.bank.exchangegenerator.dto.ExchangeRateDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Primary
public class ExchangeServiceImpl implements ExchangeService {
    @Value("${kafka.topics.exchange-topic}")
    private String exchangeTopic;
    private final KafkaTemplate<String, ExchangeRateList> kafkaTemplate;

    @Override
    public void saveRates(List<ExchangeRateDto> rates) {
        ExchangeRateList ratesList = ExchangeRateList.builder()
                .rates(rates)
                .build();
        kafkaTemplate.send(exchangeTopic, "EXCHANGE-RATES", ratesList);
    }
}
