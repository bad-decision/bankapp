package ru.azmeev.bank.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.azmeev.bank.account.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select e from UserEntity e LEFT JOIN FETCH e.accounts a LEFT JOIN FETCH a.currency where e.login = :login")
    Optional<UserEntity> findByLogin(String login);
}
