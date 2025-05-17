package ru.azmeev.bank.account.service;

import ru.azmeev.bank.account.web.dto.CurrencyDto;

import java.util.List;

public interface CurrencyService {

    List<CurrencyDto> getCurrency();
}
