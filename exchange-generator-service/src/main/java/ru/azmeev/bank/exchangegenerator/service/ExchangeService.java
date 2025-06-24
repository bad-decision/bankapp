package ru.azmeev.bank.exchangegenerator.service;

import ru.azmeev.bank.exchangegenerator.dto.ExchangeRateDto;

import java.util.List;

public interface ExchangeService {
    void saveRates(List<ExchangeRateDto> rates);
}
