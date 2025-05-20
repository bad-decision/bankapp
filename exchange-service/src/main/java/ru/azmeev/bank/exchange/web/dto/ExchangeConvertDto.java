package ru.azmeev.bank.exchange.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ExchangeConvertDto {
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal value;
}
