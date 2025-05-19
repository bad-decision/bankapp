package ru.azmeev.bank.notification.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.azmeev.bank.notification.service.NotificationService;
import ru.azmeev.bank.notification.web.dto.CashActionResult;
import ru.azmeev.bank.notification.web.dto.TransferActionResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/cash")
    public void cashOperationNotification(@RequestBody CashActionResult dto) {
        notificationService.cashOperationNotification(dto);
    }

    @PostMapping("/transfer")
    public void cashOperationNotification(@RequestBody TransferActionResult dto) {
        notificationService.transferOperationNotification(dto);
    }
}
