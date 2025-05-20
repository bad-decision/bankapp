package ru.azmeev.bank.transfer.service;

import ru.azmeev.bank.transfer.web.dto.TransferRequestDto;

public interface TransferService {

    boolean transfer(TransferRequestDto dto);
}
