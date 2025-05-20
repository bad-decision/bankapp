package ru.azmeev.bank.transfer.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransferResultDto {
    private Boolean success;
    private String message;
}
