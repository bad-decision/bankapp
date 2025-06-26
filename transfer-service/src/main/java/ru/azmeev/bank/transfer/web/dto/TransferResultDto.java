package ru.azmeev.bank.transfer.web.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResultDto {
    private UUID id;
    private Boolean success;
    private String message;
}
