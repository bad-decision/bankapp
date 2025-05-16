package ru.azmeev.bank.front.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserTransferDto {
    private String fromLogin;
    @NotNull
    private String fromCurrency;
    @NotNull
    private String toCurrency;
    @NotNull
    private BigDecimal value;
    @NotNull
    private String toLogin;
}
