package ru.azmeev.bank.transfer.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.azmeev.bank.transfer.service.TransferService;
import ru.azmeev.bank.transfer.web.dto.TransferRequestDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public boolean transfer(@RequestBody TransferRequestDto dto) {
        return transferService.transfer(dto);
    }
}
