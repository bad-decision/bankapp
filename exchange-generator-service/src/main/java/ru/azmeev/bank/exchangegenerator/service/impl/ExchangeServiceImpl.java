package ru.azmeev.bank.exchangegenerator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.exchangegenerator.service.ExchangeService;
import ru.azmeev.bank.exchangegenerator.web.dto.ExchangeRateDto;

import java.util.List;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
    @Value("${service.exchange.url}")
    private String exchangeUrl;
    private final RestClient restClient;

    @Override
    public void saveRates(List<ExchangeRateDto> rates) {
        restClient.post()
                .uri(exchangeUrl + "/api/exchange/rate")
                .body(rates)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Void.class);
    }
}
