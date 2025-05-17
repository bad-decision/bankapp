package ru.azmeev.bank.account.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.azmeev.bank.account.service.CurrencyService;
import ru.azmeev.bank.account.web.dto.CurrencyDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public List<CurrencyDto> getCurrency() {
        return currencyService.getCurrency();
    }
}