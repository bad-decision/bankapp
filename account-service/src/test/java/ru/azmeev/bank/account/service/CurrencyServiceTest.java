package ru.azmeev.bank.account.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.azmeev.bank.account.model.CurrencyEntity;
import ru.azmeev.bank.account.repository.CurrencyRepository;
import ru.azmeev.bank.account.service.impl.CurrencyServiceImpl;
import ru.azmeev.bank.account.web.dto.CurrencyDto;
import ru.azmeev.bank.account.web.mapper.CurrencyMapper;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {
    @Mock
    private CurrencyRepository currencyRepository;
    @Spy
    private CurrencyMapper currencyMapper = Mappers.getMapper(CurrencyMapper.class);
    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @AfterAll
    static void resetMocks() {
        Mockito.reset();
    }

    @Test
    void getCurrency_success() {
        CurrencyEntity currency = CurrencyEntity.builder()
                .id(1L)
                .name("USD")
                .title("Dollar")
                .build();
        when(currencyRepository.findAll()).thenReturn(List.of(currency));
        List<CurrencyDto> result = currencyService.getCurrency();
        Assertions.assertEquals(1L, result.size());
        Assertions.assertEquals(currency.getId(), result.get(0).getId());
        Assertions.assertEquals(currency.getTitle(), result.get(0).getTitle());
        Assertions.assertEquals(currency.getName(), result.get(0).getName());
        verify(currencyRepository, times(1)).findAll();
    }
}