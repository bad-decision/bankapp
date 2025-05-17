package ru.azmeev.bank.account.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDto {
    private Long id;
    private CurrencyDto currency;
    private BigDecimal value;
}
