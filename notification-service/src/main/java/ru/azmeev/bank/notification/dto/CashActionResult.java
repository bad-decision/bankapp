package ru.azmeev.bank.notification.dto;

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
