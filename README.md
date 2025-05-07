# Subtracker

Subtracker - это REST API-приложение на Spring Boot для управления пользователями и их подписчиками.
Данне хранятся в PostgreSQL. Для управления схемой используется Liquibase. 
Проект подготовлен к запуску в Docker-среде

---

## Быстрвый старт

> Требования: установлен Docker и Docker Compose

1. Соберите .jar файл проекта:

```bash
./mvnw clean package -DskipTest
```

2. Запустите приложение и БД:

```bash
docker compose up --build
```

3. Приложение будет доступно по адресу:

```
http://localhost:8080
```

---

## Что включено в проект

* **PostgreSQL** - БД с автоматическим применением миграций
* **Spring Boot** - REST API, работа с JPA
* **Liquibase** - создание схемы, таблиц и сидов для предварительного просмотра функционала
* **Docker / Docker Compose** - локальный запуск всего проекта одной командой

---

## Структура миграций Liquibase

```
src/main/resources/db/changelog/
├── changelog-master.yml
└── v1/
    ├── db.changelog-1.0-schema.yml     # создание схемы subtracker
    ├── db.changelog-1.0-init.yml       # таблицы users и subscriptions
    └── db.changelog-1.1-seed.yml       # первоначальные данные
```

---

## Данные по умолчанию

После запуска будут добавлены:

* **3 пользователя** (Ivan, Petr, Dmitry)
* **7 подписок**, включая Yandex PLUS, VK Music, Youtube Premium и др.

Можно протестировать основной API не добавляя этих данных