package ru.azmeev.bank.transfer.service;

import ru.azmeev.bank.transfer.web.dto.TransferResultDto;

public interface NotificationService {

    void notify(TransferResultDto transferResultDto);
}