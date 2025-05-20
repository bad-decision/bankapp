package ru.azmeev.bank.exchange.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.azmeev.bank.exchange.service.ExchangeService;
import ru.azmeev.bank.exchange.web.dto.ExchangeConvertDto;
import ru.azmeev.bank.exchange.web.dto.ExchangeRateDto;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping("/rate")
    public List<ExchangeRateDto> getRates() {
        return exchangeService.getRates();
    }

    @PostMapping("/rate")
    public void updateRates(@RequestBody List<ExchangeRateDto> dto) {
        exchangeService.updateRates(dto);
    }

    @PostMapping("/convert")
    public BigDecimal convert(@RequestBody ExchangeConvertDto dto) {
        return exchangeService.convert(dto);
    }
}
