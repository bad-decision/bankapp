package ru.azmeev.bank.cash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.cash.service.AccountService;
import ru.azmeev.bank.cash.service.BlockerService;
import ru.azmeev.bank.cash.service.CashService;
import ru.azmeev.bank.cash.service.NotificationService;
import ru.azmeev.bank.cash.web.dto.CashActionRequest;
import ru.azmeev.bank.cash.web.dto.CashActionResult;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CashServiceImpl implements CashService {
    private final BlockerService blockerService;
    private final NotificationService notificationService;
    private final AccountService accountService;

    @Override
    public CashActionResult process(CashActionRequest dto) {
        boolean isAllowed = blockerService.verifyOperation(dto);

        CashActionResult cashActionResult;
        if (isAllowed) {
            Boolean success = accountService.cashOperation(dto);
            cashActionResult = CashActionResult.builder()
                    .id(UUID.randomUUID())
                    .success(success)
                    .message(success ? "Cash operation was successful" : "Cash operation was not successful")
                    .build();
        } else {
            cashActionResult = CashActionResult.builder()
                    .id(UUID.randomUUID())
                    .success(false)
                    .message("Cash operation is not allowed")
                    .build();
        }

        notificationService.notify(cashActionResult);

        return cashActionResult;
    }
}
