package ru.azmeev.bank.account.web.mapper;

import org.mapstruct.Mapper;
import ru.azmeev.bank.account.model.AccountEntity;
import ru.azmeev.bank.account.web.dto.AccountDto;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(AccountEntity user);
}
