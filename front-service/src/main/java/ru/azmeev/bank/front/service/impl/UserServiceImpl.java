package ru.azmeev.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.front.model.UserEntity;
import ru.azmeev.bank.front.repository.UserRepository;
import ru.azmeev.bank.front.service.UserService;
import ru.azmeev.bank.front.web.dto.UserEditPasswordDto;
import ru.azmeev.bank.front.web.dto.UserRegistrationDto;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(UserRegistrationDto dto) {
        if (userRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        UserEntity user = UserEntity.builder()
                .login(dto.getLogin())
                .birthdate(dto.getBirthdate())
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        userRepository.save(user);
    }

    @Override
    public List<UserEntity> getUsersToTransfer() {
        return userRepository.findAll()
                .stream()
                .filter(user -> !user.getLogin().equals(getCurrentUserLogin()))
                .toList();
    }

    @Override
    public void editPassword(UserEditPasswordDto dto) {
        UserEntity user = userRepository.findByLogin(dto.getLogin())
                .orElseThrow(IllegalArgumentException::new);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void editUser(String login, String name, LocalDate birthdate) {
        UserEntity user = userRepository.findByLogin(login)
                .orElseThrow(IllegalArgumentException::new);

        if (name != null) {
            user.setName(name);
        }
        if (birthdate != null) {
            user.setBirthdate(birthdate);
        }
        userRepository.save(user);
    }

    private String getCurrentUserLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
