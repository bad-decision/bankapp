package ru.azmeev.bank.account.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.azmeev.bank.account.model.CashAction;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashActionRequest {
    private String login;
    private CashAction action;
    @NotNull
    private BigDecimal value;
    @NotNull
    private String currency;
}
