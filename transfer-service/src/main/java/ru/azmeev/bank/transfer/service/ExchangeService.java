package ru.azmeev.bank.transfer.service;

import ru.azmeev.bank.transfer.web.dto.TransferRequestDto;

import java.math.BigDecimal;

public interface ExchangeService {

    BigDecimal exchange(TransferRequestDto dto);
}
