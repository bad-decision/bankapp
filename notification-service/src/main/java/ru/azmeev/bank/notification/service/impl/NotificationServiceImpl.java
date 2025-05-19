package ru.azmeev.bank.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.notification.service.NotificationService;
import ru.azmeev.bank.notification.web.dto.CashActionResult;
import ru.azmeev.bank.notification.web.dto.TransferActionResult;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void cashOperationNotification(CashActionResult dto) {
        System.out.println(dto.getMessage());
    }

    @Override
    public void transferOperationNotification(TransferActionResult dto) {
        System.out.println(dto.getMessage());
    }
}
