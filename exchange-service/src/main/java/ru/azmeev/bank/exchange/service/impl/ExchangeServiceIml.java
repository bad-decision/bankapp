package ru.azmeev.bank.exchange.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.exchange.model.ExchangeRateEntity;
import ru.azmeev.bank.exchange.repository.ExchangeRateRepository;
import ru.azmeev.bank.exchange.service.ExchangeService;
import ru.azmeev.bank.exchange.web.dto.ExchangeRateDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExchangeServiceIml implements ExchangeService {

    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    public List<ExchangeRateDto> getRates() {
        return exchangeRateRepository.findAll().stream()
                .map(x -> ExchangeRateDto.builder()
                        .currency(x.getCurrency())
                        .value(x.getValue())
                        .build())
                .toList();
    }

    @Override
    public void updateRates(List<ExchangeRateDto> dto) {
        List<ExchangeRateEntity> rates = exchangeRateRepository.findAll();

        List<ExchangeRateEntity> ratesToUpdate = dto.stream().map(rateDto -> {
                    ExchangeRateEntity rate = rates.stream()
                            .filter(x -> x.getCurrency().equals(rateDto.getCurrency()))
                            .findFirst()
                            .orElse(null);
                    if (rate == null) {
                        rate = ExchangeRateEntity.builder()
                                .currency(rateDto.getCurrency())
                                .value(rateDto.getValue())
                                .build();
                    }
                    rate.setValue(rateDto.getValue());
                    return rate;
                })
                .toList();
        exchangeRateRepository.saveAll(ratesToUpdate);
    }
}