package ru.azmeev.bank.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.azmeev.bank.account.model.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
