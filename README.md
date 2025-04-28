# Тестовое задание
## Запуск приложения
Для начала нужно скопировать приложение локально на свой компьютер
```bash
git clone https://github.com/Vladislav3421730/GrandKapitalTask
```
Затем перейти в папку с приложением 
```bash
cd GrandKapitalTask
```
После этого нужно щапсутить приложение через Docker
```bash
docker compose up
```
После этого приложение будет доспуно по ***localhost:8080***
Для перехода на Swagger документацию можете ввести
```bash
http://localhost:8080/swagger-ui/index.html
```
## Просмотр баз данных
Если вы хотите посмотреть содержимое PostgreSQL или Redis можете выолнить следующие команды.
Для просмотра в PostgreSQL:
```bash
docker exec -it postgres sh
```
Затем
```bash
psql -U postgers -d bank
```
Для просмотра кэша в redis
```bash
docker exec -it redis sh
```
Затем 
```bash
redis-cli
```
## Используемые технологии
| Раздел       | Технологии                                                             |
|--------------|------------------------------------------------------------------------|
| Backend      | Spring Boot, Data JPA, Specification, Spring Data Validation           |
| Безопасность | Spring Security, JWT (access)                                          |
| Тестирование | Spring Boot Tests, Test Containers, JUnit, Mockito                     |
| Базы данных  | PostgreSQL, Flyway , Redis                                             |
| Кэш          | Redis, Spring Cache                                                    |
| Прочее       | Page, PageRequest, Swagger, Predicate                                  |
## Логику принятых решений 
Стоит поговорить про валидацию данных в DTO, которая сделаны через анатации от jakarta.validation.constraints и собственыее валидации через анатации и @Valid. Если пользователь отправляет неверные данные согласно валидациям, то выбрасывается MethodArgumentNotValidException, которое потом обрабатывается в глобальном обработчике. Также решена проблема N+1 в Hibernate при помощи @EntityGraph. В программе реализовано кэширование через Redis, Spring Cache, @Cachable, @CacheEvict. Также, чтобы решить проблемы с десериализацией я создал PageDto вместо Page от Spring boot. Увеличения баланса пользователей каждые 30 секунд было сделано через  @Scheduled. Чтобы баланс не превышал 207 процетов от предыдущего, начальные данные храняться в json файле, и записываются после всех успешных миграций. В программе были сделаны валидации на перевод денег, а именно: проверка на существование получателя по id, проверка на отправление самому себе, проверка на балнас (чтобы баланс не был отрицательным). 
## Реквизиты для входа
Все данные можно посмотреть в папке resources/db/migrations/V5__insert_data.sql
| ID  | Email                    | Пароль |
|-----|--------------------------|--------|
| 1   | vlad@gmail.com           | q1w2e3 |
| 1   | vlados@gmail.com         | q1w2e3 |
| 2   | egor@gmail.com           | q1w2e3 |
| 2   | egorik@gmail.com         | q1w2e3 |
| 3   | nastya@gmail.com         | q1w2e3 |
| 3   | nastya2@gmail.com        | q1w2e3 |
| 4   | liza@gmail.com           | q1w2e3 |
| 4   | liza2@gmail.com          | q1w2e3 |
| 5   | alexey@gmail.com         | q1w2e3 |
| 5   | a.sharpov@gmail.com      | q1w2e3 |
| 6   | maria@gmail.com          | q1w2e3 |
| 6   | masha23@gmail.com        | q1w2e3 |
| 7   | ivan1985@gmail.com       | q1w2e3 |
| 7   | ivanko@gmail.com         | q1w2e3 |
| 8   | olga11@gmail.com         | q1w2e3 |
| 8   | olyushka@gmail.com       | q1w2e3 |
| 9   | dmitriy99@gmail.com      | q1w2e3 |
| 9   | dimasik@gmail.com        | q1w2e3 |
| 10  | sveta@gmail.com          | q1w2e3 |
| 10  | svetik87@gmail.com       | q1w2e3 |
| 11  | artemka@gmail.com        | q1w2e3 |
| 11  | artem21@gmail.com        | q1w2e3 |
| 12  | yulia94@gmail.com        | q1w2e3 |
| 12  | julia8@gmail.com         | q1w2e3 |
| 13  | nikita2000@gmail.com     | q1w2e3 |
| 13  | nik@gmail.com            | q1w2e3 |
| 14  | ekaterina@gmail.com      | q1w2e3 |
| 14  | katusha@gmail.com        | q1w2e3 |
