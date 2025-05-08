# Subtracker

**Subtracker** — REST API-приложение на Spring Boot для управления пользователями и их подписками. Данные хранятся в PostgreSQL. Liquibase отвечает за схему и сиды. Всё готово к запуску через Docker Compose.

---

## 🚀 Быстрый старт

> Требования: установлен Docker и Docker Compose

1. Соберите `.jar` файл:
```bash
./mvnw clean package -DskipTests
```

2. Запустите проект:
```bash
docker compose up --build
```

3. Приложение будет доступно по адресу:
```
http://localhost:8080
```

---

## 🔗 Swagger-документация

- UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 📚 Примеры вызова API

📎 Полный список примеров ручек:  
[docs/api-endpoints.md](docs/api-endpoints.md)

---

## 🧩 Используемые технологии

- Spring Boot (REST API, JPA)
- PostgreSQL + Liquibase (миграции и сиды)
- Docker / Docker Compose (сборка и запуск)
- Swagger / OpenAPI (документация)

---

## 📁 Liquibase

```
src/main/resources/db/changelog/
├── changelog-master.yml
└── v1/
    ├── db.changelog-1.0-schema.yml     # создание схемы и таблиц
    ├── db.changelog-1.0-init.yml       # users, subscriptions
    └── db.changelog-1.1-seed.yml       # сиды и синхронизация sequence
```

---

## 📦 Сиды по умолчанию

3 пользователя:
- Ivan — ivan@mail.ru
- Petr — petr@mail.ru
- Dmitry — dmitry@mail.ru

7 подписок на разные сервисы:
- Netflix, YouTube Premium, VK Music, Yandex PLUS

API готов к использованию сразу после запуска.
