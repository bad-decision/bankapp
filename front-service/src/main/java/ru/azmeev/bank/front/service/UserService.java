package ru.azmeev.bank.front.service;

import ru.azmeev.bank.front.model.UserEntity;
import ru.azmeev.bank.front.web.dto.UserEditPasswordDto;
import ru.azmeev.bank.front.web.dto.UserRegistrationDto;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    void register(UserRegistrationDto dto);

    void editPassword(UserEditPasswordDto dto);

    void editUser(String login, String name, LocalDate birthdate);

    List<UserEntity> getUsersToTransfer();
}
