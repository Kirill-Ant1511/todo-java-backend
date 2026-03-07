# Todosuck - Todo Service API

REST API сервис для управления задачами (todo).

## Технологии

- Java 17+
- Spring Boot
- PostgreSQL
- Gradle

## Запуск проекта

### С Docker

```bash
# Запустить PostgreSQL и приложение
docker-compose up -d --build
```

### Без Docker

1. **Запустите PostgreSQL:**
```bash
docker run -d \
  --name task_postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=root_task_db_password \
  -e POSTGRES_DB=task_db \
  -p 5433:5432 \
  postgres:16.2-alpine
```

2. **Запустите приложение:**
```bash
./gradlew :todo-service:bootRun
```

Или соберите JAR и запустите:
```bash
./gradlew :todo-service:bootJar
java -jar todo-service/build/libs/todo-service-*.jar
```

Приложение будет доступно по адресу: `http://localhost:8080`

---

## API Документация

### Base URL

```
http://localhost:8080/api/task
```

---

## Эндпоинты

### 1. Получить все задачи

**GET** `/api/task`

Возвращает список всех задач.

#### Ответ

| Код | Описание |
|-----|----------|
| 200 | Успешно |

#### Пример запроса

```bash
curl -X GET http://localhost:8080/api/task
```

#### Пример ответа

```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "createdAt": "2024-01-15T10:30:00",
    "title": "Купить продукты",
    "description": "Молоко, хлеб, яйца",
    "ownerId": "123e4567-e89b-12d3-a456-426614174000",
    "ownerName": "Иван",
    "performBy": null,
    "isDone": false,
    "comments": []
  }
]
```

---

### 2. Получить задачу по ID

**GET** `/api/task/{id}`

Возвращает задачу по указанному идентификатору.

#### Параметры пути

| Параметр | Тип | Описание |
|----------|-----|----------|
| `id` | UUID | Идентификатор задачи |

#### Ответ

| Код | Описание |
|-----|----------|
| 200 | Успешно |
| 404 | Задача не найдена |

#### Пример запроса

```bash
curl -X GET http://localhost:8080/api/task/550e8400-e29b-41d4-a716-446655440000
```

#### Пример ответа

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2024-01-15T10:30:00",
  "title": "Купить продукты",
  "description": "Молоко, хлеб, яйца",
  "ownerId": "123e4567-e89b-12d3-a456-426614174000",
  "ownerName": "Иван",
  "performBy": null,
  "isDone": false,
  "comments": [
    {
      "id": "660e8400-e29b-41d4-a716-446655440001",
      "ownerId": "123e4567-e89b-12d3-a456-426614174000",
      "ownerName": "Иван",
      "content": "Не забыть сыр!"
    }
  ]
}
```

---

### 3. Создать задачу

**POST** `/api/task`

Создает новую задачу.

#### Тело запроса

| Поле | Тип | Обязательно | Описание |
|------|-----|-------------|----------|
| `title` | String | Да | Название задачи |
| `description` | String | Нет | Описание задачи |
| `ownerId` | UUID | Да | ID владельца задачи |

#### Ответ

| Код | Описание |
|-----|----------|
| 200 | Задача создана |
| 400 | Неверные данные |

#### Пример запроса

```bash
curl -X POST http://localhost:8080/api/task \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Купить продукты",
    "description": "Молоко, хлеб, яйца",
    "ownerId": "123e4567-e89b-12d3-a456-426614174000"
  }'
```

#### Пример ответа

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2024-01-15T10:30:00",
  "title": "Купить продукты",
  "description": "Молоко, хлеб, яйца",
  "ownerId": "123e4567-e89b-12d3-a456-426614174000",
  "ownerName": null,
  "performBy": null,
  "isDone": false,
  "comments": []
}
```

---

### 4. Обновить задачу

**PUT** `/api/task/{id}`

Обновляет существующую задачу.

#### Параметры пути

| Параметр | Тип | Описание |
|----------|-----|----------|
| `id` | UUID | Идентификатор задачи |

#### Тело запроса

| Поле | Тип | Обязательно | Описание |
|------|-----|-------------|----------|
| `title` | String | Да | Название задачи |
| `description` | String | Нет | Описание задачи |
| `ownerId` | UUID | Да | ID владельца задачи |

#### Ответ

| Код | Описание |
|-----|----------|
| 200 | Задача обновлена |
| 404 | Задача не найдена |
| 400 | Неверные данные |

#### Пример запроса

```bash
curl -X PUT http://localhost:8080/api/task/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Купить продукты (обновлено)",
    "description": "Молоко, хлеб, яйца, сыр",
    "ownerId": "123e4567-e89b-12d3-a456-426614174000"
  }'
```

#### Пример ответа

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2024-01-15T10:30:00",
  "title": "Купить продукты (обновлено)",
  "description": "Молоко, хлеб, яйца, сыр",
  "ownerId": "123e4567-e89b-12d3-a456-426614174000",
  "ownerName": null,
  "performBy": null,
  "isDone": false,
  "comments": []
}
```

---

### 5. Удалить задачу

**DELETE** `/api/task/{id}`

Удаляет задачу по указанному идентификатору.

#### Параметры пути

| Параметр | Тип | Описание |
|----------|-----|----------|
| `id` | UUID | Идентификатор задачи |

#### Ответ

| Код | Описание |
|-----|----------|
| 200 | Задача удалена |
| 404 | Задача не найдена |

#### Пример запроса

```bash
curl -X DELETE http://localhost:8080/api/task/550e8400-e29b-41d4-a716-446655440000
```

#### Пример ответа

```
HTTP/1.1 200 OK
(пустое тело ответа)
```

---

## Модели данных

### RequestTaskDto (запрос)

```json
{
  "title": "string",
  "description": "string",
  "ownerId": "uuid"
}
```

### ResponseTaskDto (ответ)

```json
{
  "id": "uuid",
  "createdAt": "datetime",
  "title": "string",
  "description": "string",
  "ownerId": "uuid",
  "ownerName": "string",
  "performBy": "uuid",
  "isDone": "boolean",
  "comments": [ResponseCommentDto]
}
```

### ResponseCommentDto (комментарий)

```json
{
  "id": "uuid",
  "ownerId": "uuid",
  "ownerName": "string",
  "content": "string"
}
```

---

## Конфигурация

Файл `application.properties`:

| Параметр | Значение по умолчанию | Описание |
|----------|----------------------|----------|
| `server.port` | 8080 | Порт приложения |
| `spring.datasource.url` | jdbc:postgresql://localhost:5433/task_db | URL базы данных |
| `spring.datasource.username` | postgres | Имя пользователя БД |
| `spring.datasource.password` | root_task_db_password | Пароль БД |

---

## Лицензия

MIT

