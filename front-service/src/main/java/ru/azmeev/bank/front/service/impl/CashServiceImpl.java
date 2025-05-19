package ru.azmeev.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.front.service.CashService;
import ru.azmeev.bank.front.web.dto.CashActionResult;
import ru.azmeev.bank.front.web.dto.CashActionRequest;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Service
@RequiredArgsConstructor
public class CashServiceImpl implements CashService {

    @Value("${service.cash.url}")
    private String cashUrl;
    private final RestClient restClient;

    @Override
    public CashActionResult processCashAction(CashActionRequest dto) {
        return restClient.post()
                .uri(cashUrl + "/api/cash/process")
                .body(dto)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(CashActionResult.class);
    }
}
