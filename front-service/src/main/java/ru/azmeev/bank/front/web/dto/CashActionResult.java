package ru.azmeev.bank.front.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashActionResult {
    private Boolean success;
    private String message;
}
