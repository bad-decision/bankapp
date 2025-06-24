package ru.azmeev.bank.exchange.web.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateList {
    private List<ExchangeRateDto> rates;
}
