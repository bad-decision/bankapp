package ru.azmeev.bank.cash.service;

import ru.azmeev.bank.cash.web.dto.CashActionRequest;

public interface AccountService {

    boolean cashOperation(CashActionRequest dto);
}
