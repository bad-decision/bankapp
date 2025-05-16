package ru.azmeev.bank.front.service;

import ru.azmeev.bank.front.web.dto.UserCashDto;

public interface CashService {

    void processCashAction(UserCashDto dto);
}
