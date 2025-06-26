package ru.azmeev.bank.exchangegenerator.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDto {
    private String currency;
    private BigDecimal value;
}
