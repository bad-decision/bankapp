package ru.azmeev.bank.transfer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.transfer.service.BlockerService;
import ru.azmeev.bank.transfer.service.ExchangeService;
import ru.azmeev.bank.transfer.service.NotificationService;
import ru.azmeev.bank.transfer.service.TransferService;
import ru.azmeev.bank.transfer.web.dto.AccountTransferRequestDto;
import ru.azmeev.bank.transfer.web.dto.TransferRequestDto;
import ru.azmeev.bank.transfer.web.dto.TransferResultDto;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@RequiredArgsConstructor
@Service
public class TransferServiceImpl implements TransferService {

    @Value("${service.account.url}")
    private String accountUrl;
    private final RestClient restClient;
    private final BlockerService blockerService;
    private final ExchangeService exchangeService;
    private final NotificationService notificationService;

    @Override
    public boolean transfer(TransferRequestDto dto) {
        boolean isAllowed = blockerService.verifyOperation(dto);

        TransferResultDto transferResultDto;
        if (isAllowed) {
            BigDecimal toValue = exchangeService.exchange(dto);

            AccountTransferRequestDto accountTransferRequestDto = AccountTransferRequestDto.builder()
                    .toCurrency(dto.getToCurrency())
                    .fromCurrency(dto.getFromCurrency())
                    .fromLogin(dto.getFromLogin())
                    .toLogin(dto.getToLogin())
                    .fromValue(dto.getValue())
                    .toValue(toValue)
                    .build();

            Boolean transferResult = transferInternal(accountTransferRequestDto);

            transferResultDto = TransferResultDto.builder()
                    .id(UUID.randomUUID())
                    .success(transferResult)
                    .fromLogin(dto.getFromLogin())
                    .toLogin(dto.getToLogin())
                    .message(transferResult ? "Transfer operation was successfully" : "Transfer operation was not successfully")
                    .build();

        } else {
            transferResultDto = TransferResultDto.builder()
                    .id(UUID.randomUUID())
                    .success(false)
                    .fromLogin(dto.getFromLogin())
                    .toLogin(dto.getToLogin())
                    .message("Transfer operation is not allowed")
                    .build();
        }

        notificationService.notify(transferResultDto);

        return transferResultDto.getSuccess();
    }

    private Boolean transferInternal(AccountTransferRequestDto accountTransferRequestDto) {
        return restClient.post()
                .uri(accountUrl + "/api/user/transfer")
                .body(accountTransferRequestDto)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Boolean.class);
    }
}
