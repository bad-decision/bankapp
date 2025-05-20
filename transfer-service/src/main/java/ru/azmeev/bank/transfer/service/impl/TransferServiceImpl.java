package ru.azmeev.bank.transfer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.transfer.service.TransferService;
import ru.azmeev.bank.transfer.web.dto.AccountTransferRequestDto;
import ru.azmeev.bank.transfer.web.dto.TransferRequestDto;
import ru.azmeev.bank.transfer.web.dto.TransferResultDto;

import java.math.BigDecimal;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@RequiredArgsConstructor
@Service
public class TransferServiceImpl implements TransferService {

    @Value("${service.account.url}")
    private String accountUrl;
    @Value("${service.exchange.url}")
    private String exchangeUrl;
    @Value("${service.blocker.url}")
    private String blockerUrl;
    @Value("${service.notification.url}")
    private String notificationUrl;
    private final RestClient restClient;

    @Override
    public boolean transfer(TransferRequestDto dto) {
        Boolean isAllowed = restClient.post()
                .uri(blockerUrl + "/api/blocker/verify")
                .body(dto)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Boolean.class);

        TransferResultDto transferResultDto;
        if (isAllowed) {
            BigDecimal toValue = restClient.post()
                    .uri(exchangeUrl + "/api/exchange/convert")
                    .body(dto)
                    .attributes(clientRegistrationId("keycloak"))
                    .retrieve()
                    .body(BigDecimal.class);

            AccountTransferRequestDto accountTransferRequestDto = AccountTransferRequestDto.builder()
                    .toCurrency(dto.getToCurrency())
                    .fromCurrency(dto.getFromCurrency())
                    .fromLogin(dto.getFromLogin())
                    .toLogin(dto.getToLogin())
                    .fromValue(dto.getValue())
                    .toValue(toValue)
                    .build();

            Boolean transferResult = restClient.post()
                    .uri(accountUrl + "/api/user/transfer")
                    .body(accountTransferRequestDto)
                    .attributes(clientRegistrationId("keycloak"))
                    .retrieve()
                    .body(Boolean.class);

            transferResultDto = TransferResultDto.builder()
                    .success(transferResult)
                    .message(transferResult ? "Transfer operation was successfully" : "Transfer operation was not successfully")
                    .build();

        } else {
            transferResultDto = TransferResultDto.builder()
                    .success(false)
                    .message("Transfer operation is not allowed")
                    .build();
        }

        restClient.post()
                .uri(notificationUrl + "/api/notification/transfer")
                .body(transferResultDto)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Void.class);

        return transferResultDto.getSuccess();
    }
}
