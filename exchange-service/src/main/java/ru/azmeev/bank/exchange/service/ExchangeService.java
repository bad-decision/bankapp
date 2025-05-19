package ru.azmeev.bank.exchange.service;

import ru.azmeev.bank.exchange.web.dto.ExchangeRateDto;

import java.util.List;

public interface ExchangeService {

    List<ExchangeRateDto> getRates();

    void updateRates(List<ExchangeRateDto> dto);
}
