# API Endpoints — Subtracker

Полный список ручек для работы с пользователями и подписками.

Базовый URL:

```
http://localhost:8080/api
```

---

## Пользователи

### POST /users — создать пользователя

**Пример запроса:**

```json
{
  "name": "Иван Иванов",
  "email": "ivan@example.com"
}
```

**Ответ:** `201 Created`

```json
{
  "id": 1,
  "name": "Иван Иванов",
  "email": "ivan@example.com"
}
```

**Ошибки:**

* `400 Bad Request`
* `409 Conflict`

---

### GET /users/{id} — получить пользователя по ID

**Пример:** `GET /users/1`

**Ответ:** `200 OK`

```json
{
  "id": 1,
  "name": "Иван Иванов",
  "email": "ivan@example.com"
}
```

**Ошибка:** `404 Not Found`

---

### PUT /users/{id} — обновить пользователя

**Пример запроса:**

```json
{
  "name": "Петр Петров",
  "email": "petya@example.com"
}
```

**Ответ:** `200 OK`

```json
{
  "id": 1,
  "name": "Петр Петров",
  "email": "petya@example.com"
}
```

**Ошибки:**

* `400 Bad Request`
* `404 Not Found`
* `409 Conflict`

---

### DELETE /users/{id} — удалить пользователя

**Пример:** `DELETE /users/1`

**Ответ:** `204 No Content`

**Ошибка:** `404 Not Found`

---

## Подписки

### POST /users/{id}/subscriptions — добавить подписку

**Пример запроса:**

```json
{
  "serviceName": "Netflix"
}
```

**Ответ:** `201 Created`

```json
{
  "id": 1,
  "serviceName": "Netflix"
}
```

**Ошибки:**

* `400 Bad Request`
* `404 Not Found` — пользователь не найден
* `409 Conflict` — подписка уже существует

---

### GET /users/{id}/subscriptions — получить подписки пользователя

**Пример:** `GET /users/1/subscriptions`

**Ответ:** `200 OK`

```json
[
  {
    "id": 1,
    "serviceName": "Netflix"
  }
]
```

**Ошибка:** `404 Not Found`

---

### DELETE /users/{id}/subscriptions/{subId} — удалить подписку

**Пример:** `DELETE /users/1/subscriptions/2`

**Ответ:** `204 No Content`

**Ошибка:** `404 Not Found` — подписка не найдена или не принадлежит пользователю

---

### GET /subscriptions/top — получить топ-3 популярных подписок

**Пример:** `GET /subscriptions/top`

**Ответ:** `200 OK`

```json
[
  {
    "serviceName": "Yandex PLUS",
    "count": 3
  },
  {
    "serviceName": "Netflix",
    "count": 2
  },
  {
    "serviceName": "YouTube Premium",
    "count": 2
  }
]
```

