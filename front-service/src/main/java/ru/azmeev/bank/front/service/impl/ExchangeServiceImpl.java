package ru.azmeev.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.front.model.Currency;
import ru.azmeev.bank.front.model.ExchangeRate;
import ru.azmeev.bank.front.service.AccountService;
import ru.azmeev.bank.front.service.ExchangeService;
import ru.azmeev.bank.front.web.dto.ExchangeRateDto;

import java.util.List;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    @Value("${service.exchange.url}")
    private String exchangeUrl;
    private final RestClient restClient;
    private final AccountService accountService;

    @Override
    public List<ExchangeRate> getExchangeRates() {
        List<Currency> currency = accountService.getCurrency();
        List<ExchangeRateDto> rates = restClient.get()
                .uri(exchangeUrl + "/api/exchange/rate")
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return currency.stream()
                .map(x -> ExchangeRate.builder()
                        .name(x.getName())
                        .title(x.getTitle())
                        .value(rates.stream()
                                .filter(y -> y.getCurrency().equals(x.getName()))
                                .map(ExchangeRateDto::getValue)
                                .findFirst()
                                .orElse(null))
                        .build())
                .toList();
    }
}
