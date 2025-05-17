package ru.azmeev.bank.front.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class Account {
    private Currency currency;
    private Boolean exists;
    private BigDecimal value;
}
