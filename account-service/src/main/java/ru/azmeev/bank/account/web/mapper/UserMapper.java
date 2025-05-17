package ru.azmeev.bank.account.web.mapper;

import org.mapstruct.Mapper;
import ru.azmeev.bank.account.model.UserEntity;
import ru.azmeev.bank.account.web.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserEntity user);
}
