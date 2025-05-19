package ru.azmeev.bank.front.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.azmeev.bank.front.model.ExchangeRate;
import ru.azmeev.bank.front.service.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    private final ExchangeService exchangeService;

    @GetMapping("/rates")
    public List<ExchangeRate> rate() {
        return exchangeService.getExchangeRates();
    }
}