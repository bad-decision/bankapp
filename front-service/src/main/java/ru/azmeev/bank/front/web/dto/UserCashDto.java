package ru.azmeev.bank.front.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.azmeev.bank.front.model.CashAction;

import java.math.BigDecimal;

@Getter
@Setter
public class UserCashDto {
    private String login;
    private CashAction action;
    @NotNull
    private BigDecimal value;
    @NotNull
    private String currency;
}
