package ru.azmeev.bank.front.service;

import ru.azmeev.bank.front.model.ExchangeRate;

import java.util.List;

public interface ExchangeService {

    List<ExchangeRate> getExchangeRates();
}
