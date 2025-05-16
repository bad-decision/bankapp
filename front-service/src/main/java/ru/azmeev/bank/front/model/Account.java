package ru.azmeev.bank.front.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Account {
    private String login;
    private Currency currency;
    private Boolean exists;
    private BigDecimal value;
}
