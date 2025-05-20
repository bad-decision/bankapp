package ru.azmeev.bank.transfer.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TransferRequestDto {
    @NotNull
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
