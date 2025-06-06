package ru.azmeev.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.front.service.TransferService;
import ru.azmeev.bank.front.web.dto.UserTransferDto;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    @Value("${service.transfer.url}")
    private String transferUrl;
    private final RestClient restClient;

    @Override
    public void processTransfer(UserTransferDto dto) {
        restClient.post()
                .uri(transferUrl + "/api/transfer")
                .body(dto)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Void.class);
    }
}
