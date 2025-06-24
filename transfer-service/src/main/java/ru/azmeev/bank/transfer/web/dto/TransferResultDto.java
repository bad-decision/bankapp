package ru.azmeev.bank.transfer.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class TransferResultDto {
    private UUID id;
    private Boolean success;
    private String message;
}
