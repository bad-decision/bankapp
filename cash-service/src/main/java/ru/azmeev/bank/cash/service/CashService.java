package ru.azmeev.bank.cash.service;

import ru.azmeev.bank.cash.web.dto.CashActionRequest;
import ru.azmeev.bank.cash.web.dto.CashActionResult;

public interface CashService {

    CashActionResult process(CashActionRequest dto);
}
