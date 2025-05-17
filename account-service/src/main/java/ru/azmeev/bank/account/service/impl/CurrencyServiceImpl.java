package ru.azmeev.bank.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.account.repository.CurrencyRepository;
import ru.azmeev.bank.account.service.CurrencyService;
import ru.azmeev.bank.account.web.dto.CurrencyDto;
import ru.azmeev.bank.account.web.mapper.CurrencyMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    @Override
    public List<CurrencyDto> getCurrency() {
        return currencyRepository.findAll()
                .stream()
                .map(currencyMapper::toDto)
                .toList();
    }
}
