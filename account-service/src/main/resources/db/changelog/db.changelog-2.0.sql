--liquibase formatted sql

--changeset azmeev:1
INSERT INTO bank_currency
VALUES (1, 'Рубли', 'RUB'),
       (2, 'Евро', 'EUR'),
       (3, 'Доллары', 'USD');