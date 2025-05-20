package ru.azmeev.bank.exchange.service;

import ru.azmeev.bank.exchange.web.dto.ExchangeConvertDto;
import ru.azmeev.bank.exchange.web.dto.ExchangeRateDto;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeService {

    List<ExchangeRateDto> getRates();

    void updateRates(List<ExchangeRateDto> dto);

    BigDecimal convert(ExchangeConvertDto dto);
}
