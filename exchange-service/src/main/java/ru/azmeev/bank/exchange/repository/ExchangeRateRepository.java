package ru.azmeev.bank.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.azmeev.bank.exchange.model.ExchangeRateEntity;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long> {
}
