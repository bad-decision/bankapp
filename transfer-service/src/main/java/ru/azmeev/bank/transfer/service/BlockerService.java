package ru.azmeev.bank.transfer.service;

import ru.azmeev.bank.transfer.web.dto.TransferRequestDto;

public interface BlockerService {

    boolean verifyOperation(TransferRequestDto dto);
}
