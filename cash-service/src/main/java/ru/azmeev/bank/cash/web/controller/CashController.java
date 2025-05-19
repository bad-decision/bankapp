package ru.azmeev.bank.cash.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.azmeev.bank.cash.service.CashService;
import ru.azmeev.bank.cash.web.dto.CashActionRequest;
import ru.azmeev.bank.cash.web.dto.CashActionResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cash")
public class CashController {

    private final CashService cashService;

    @PostMapping("/process")
    public CashActionResult process(@RequestBody CashActionRequest dto) {
        return cashService.process(dto);
    }
}
