package ru.azmeev.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.front.model.ExchangeRate;
import ru.azmeev.bank.front.service.ExchangeService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
    @Override
    public List<ExchangeRate> getExchangeRates(String currencyName) {
        return List.of(ExchangeRate.builder()
                .title("EUR")
                .value(BigDecimal.valueOf(100))
                .name("EVRO")
                .build());
    }
}
