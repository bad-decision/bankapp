package ru.azmeev.bank.front.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.azmeev.bank.front.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String userName);
}
