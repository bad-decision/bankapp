# Bank Application
Микросервисное приложение «Банк»:
- фронт (front-service);
- сервис аккаунтов (account-service);
- сервис обналичивания денег (cash-service);
- сервис перевода денег между счетами одного или двух аккаунтов (transfer-service);
- сервис конвертации валют (exchange-service);
- сервис генерации курсов валют (exchange-generator-service);
- сервис блокировки подозрительных операций (blocker-service);
- сервис уведомлений (notification-service)

Используемые технологии:
- Spring Boot Framework
- Spring Web MVC
- Spring Data JPA
- Thymeleaf
- Lombok
- PostgreSQL
- Keycloak

Запуск приложения:
```
docker compose up
```

- После запуска приложение доступно по адресу: http://localhost:8010