package ru.azmeev.bank.cash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.cash.service.BlockerService;
import ru.azmeev.bank.cash.web.dto.CashActionRequest;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Service
@RequiredArgsConstructor
public class BlockerServiceImpl implements BlockerService {
    @Value("${service.blocker.url}")
    private String blockerUrl;
    private final RestClient restClient;

    @Override
    public boolean verifyOperation(CashActionRequest dto) {
        return restClient.post()
                .uri(blockerUrl + "/api/blocker/verify")
                .body(dto)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Boolean.class);
    }
}
