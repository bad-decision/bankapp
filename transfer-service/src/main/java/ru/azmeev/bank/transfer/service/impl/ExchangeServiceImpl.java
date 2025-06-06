package ru.azmeev.bank.transfer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.transfer.service.ExchangeService;
import ru.azmeev.bank.transfer.web.dto.TransferRequestDto;

import java.math.BigDecimal;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
    @Value("${service.exchange.url}")
    private String exchangeUrl;
    private final RestClient restClient;

    @Override
    public BigDecimal exchange(TransferRequestDto dto) {
        return restClient.post()
                .uri(exchangeUrl + "/api/exchange/convert")
                .body(dto)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(BigDecimal.class);
    }
}
