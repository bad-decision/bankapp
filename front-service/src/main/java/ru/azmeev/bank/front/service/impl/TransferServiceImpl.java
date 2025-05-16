package ru.azmeev.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.front.service.TransferService;
import ru.azmeev.bank.front.web.dto.UserTransferDto;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    @Override
    public void processTransfer(UserTransferDto dto) {

    }
}
