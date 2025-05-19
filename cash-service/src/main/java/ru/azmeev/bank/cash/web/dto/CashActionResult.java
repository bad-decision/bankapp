package ru.azmeev.bank.cash.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CashActionResult {
    private Boolean success;
    private String message;
}
