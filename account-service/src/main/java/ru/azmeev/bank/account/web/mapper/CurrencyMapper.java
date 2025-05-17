package ru.azmeev.bank.account.web.mapper;

import org.mapstruct.Mapper;
import ru.azmeev.bank.account.model.CurrencyEntity;
import ru.azmeev.bank.account.web.dto.CurrencyDto;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyDto toDto(CurrencyEntity user);
}
