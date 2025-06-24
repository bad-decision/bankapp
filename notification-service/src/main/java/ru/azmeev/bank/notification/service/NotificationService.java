package ru.azmeev.bank.notification.service;

import ru.azmeev.bank.notification.dto.CashActionResult;
import ru.azmeev.bank.notification.dto.TransferActionResult;

public interface NotificationService {

    void cashOperationNotification(CashActionResult dto);

    void transferOperationNotification(TransferActionResult dto);
}