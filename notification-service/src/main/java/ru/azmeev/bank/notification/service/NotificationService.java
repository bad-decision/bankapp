package ru.azmeev.bank.notification.service;

import ru.azmeev.bank.notification.web.dto.CashActionResult;
import ru.azmeev.bank.notification.web.dto.TransferActionResult;

public interface NotificationService {

    void cashOperationNotification(CashActionResult dto);

    void transferOperationNotification(TransferActionResult dto);
}
