package ru.azmeev.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.front.service.CashService;
import ru.azmeev.bank.front.web.dto.UserCashDto;

@Service
@RequiredArgsConstructor
public class CashServiceImpl implements CashService {
    @Override
    public void processCashAction(UserCashDto dto) {

    }
}
