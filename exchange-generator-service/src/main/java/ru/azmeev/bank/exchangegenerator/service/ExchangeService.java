package ru.azmeev.bank.exchangegenerator.service;

import ru.azmeev.bank.exchangegenerator.web.dto.ExchangeRateDto;

import java.util.List;

public interface ExchangeService {
    void saveRates(List<ExchangeRateDto> rates);
}
