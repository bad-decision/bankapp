package ru.azmeev.bank.front.service;

import ru.azmeev.bank.front.web.dto.CashActionResult;
import ru.azmeev.bank.front.web.dto.CashActionRequest;

public interface CashService {

    CashActionResult processCashAction(CashActionRequest dto);
}
