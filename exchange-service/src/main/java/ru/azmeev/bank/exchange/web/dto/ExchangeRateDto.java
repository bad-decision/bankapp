package ru.azmeev.bank.exchange.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ExchangeRateDto {
    private String currency;
    private BigDecimal value;
}
