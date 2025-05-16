package ru.azmeev.bank.front.service;

import ru.azmeev.bank.front.web.dto.UserTransferDto;

public interface TransferService {

    void processTransfer(UserTransferDto dto);
}
