package ru.azmeev.bank.front.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ExchangeRate {
    private String title;
    private String name;
    private BigDecimal value;
}
