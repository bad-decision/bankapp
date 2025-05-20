package ru.azmeev.bank.account.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class AccountTransferRequestDto {
    @NotNull
    private String fromLogin;
    @NotNull
    private String fromCurrency;
    @NotNull
    private BigDecimal fromValue;
    @NotNull
    private BigDecimal toValue;
    @NotNull
    private String toLogin;
    @NotNull
    private String toCurrency;
}
