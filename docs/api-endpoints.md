# API Endpoints — Subtracker

Полный список ручек для работы с пользователями.

Базовый URL:

```
http://localhost:8080/api/users
```

---

## POST /api/users — создать пользователя

**Пример запроса:**

```json
{
  "name": "Иван Иванов",
  "email": "ivan@example.com"
}
```

**Ответ:**

* `201 Created`

```json
{
  "id": 1,
  "name": "Иван Иванов",
  "email": "ivan@example.com"
}
```

**Ошибки:**

* `400 Bad Request`

```json
{
  "error": "email: должно быть корректным адресом"
}
```

* `409 Conflict`

```json
{
  "error": "Пользователь с таким email уже существует"
}
```

---

## GET /api/users/{id} — получить пользователя по ID

**Пример:**

```
GET /api/users/1
```

**Ответ:**

* `200 OK`

```json
{
  "id": 1,
  "name": "Иван Иванов",
  "email": "ivan@example.com"
}
```

**Ошибка:**

* `404 Not Found`

```json
{
  "error": "Пользователь с id 1 не найден"
}
```

---

## UT /api/users/{id} — обновить пользователя

**Пример запроса:**

```json
{
  "name": "Петр Петров",
  "email": "petya@example.com"
}
```

**Ответ:**

* `200 OK`

```json
{
  "id": 1,
  "name": "Петр Петров",
  "email": "petya@example.com"
}
```

**Ошибки:**

* `400 Bad Request`

```json
{
  "error": "name: не должно быть пустым"
}
```

* `404 Not Found`

```json
{
  "error": "Пользователь с id 1 не найден"
}
```

* `409 Conflict`

```json
{
  "error": "Пользователь с таким email уже существует"
}
```

---

## DELETE /api/users/{id} — удалить пользователя

**Пример:**

```
DELETE /api/users/1
```

**Ответ:**

* `204 No Content`

**Ошибка:**

* `404 Not Found`

```json
{
  "error": "Пользователь с id 1 не найден"
}
```
