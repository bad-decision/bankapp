package ru.azmeev.bank.exchangegenerator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.exchangegenerator.service.ExchangeGeneratorService;
import ru.azmeev.bank.exchangegenerator.service.ExchangeService;
import ru.azmeev.bank.exchangegenerator.web.dto.ExchangeRateDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class ExchangeGeneratorServiceImpl implements ExchangeGeneratorService {
    private final ExchangeService exchangeService;
    private final Random random = new Random();

    @Scheduled(fixedRate = 1000)
    @Override
    public void generateRates() {
        try {
            exchangeService.saveRates(getRates());
        } catch (Exception e) {
            System.err.println("Error sending request: " + e.getMessage());
        }
    }

    private List<ExchangeRateDto> getRates() {
        ExchangeRateDto usd = ExchangeRateDto.builder()
                .currency("USD")
                .value(generateValue())
                .build();
        ExchangeRateDto rub = ExchangeRateDto.builder()
                .currency("RUB")
                .value(BigDecimal.valueOf(1))
                .build();
        ExchangeRateDto euro = ExchangeRateDto.builder()
                .currency("EUR")
                .value(generateValue())
                .build();
        return List.of(usd, euro, rub);
    }

    private BigDecimal generateValue() {
        return BigDecimal.valueOf(random.nextDouble() * 100);
    }
}