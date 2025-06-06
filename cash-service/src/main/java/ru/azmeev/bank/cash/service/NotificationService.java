package ru.azmeev.bank.cash.service;

import ru.azmeev.bank.cash.web.dto.CashActionResult;

public interface NotificationService {

    void notify(CashActionResult cashActionResult);
}
