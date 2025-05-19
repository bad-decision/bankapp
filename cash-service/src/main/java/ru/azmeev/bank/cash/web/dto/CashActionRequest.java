package ru.azmeev.bank.cash.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.azmeev.bank.cash.model.CashAction;

import java.math.BigDecimal;

@Getter
@Setter
public class CashActionRequest {
    private String login;
    private CashAction action;
    @NotNull
    private BigDecimal value;
    @NotNull
    private String currency;
}
