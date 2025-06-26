package ru.azmeev.bank.cash.web.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashActionResult {
    private UUID id;
    private Boolean success;
    private String message;
}
