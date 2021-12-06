## Курсовой проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка

### Задача
Автоматизировать сценарии (как позитивные, так и негативные) покупки тура.

__Описание приложения__  
Приложение(веб-сервис) предлагает купить тур по определённой цене двумя способами:
- Обычная оплата по дебетовой карте
- Выдача кредита по данным банковской карты

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам.  
Сервисы обрабатывает только специальные номера карт, которые предоставлены для тестирования:
- APPROVED карта - 1111 2222 3333 4444
- DECLINED карта - 5555 6666 7777 8888

Приложение должно в собственной БД сохранять информацию о способе платёжа и статус (при этом данные карт сохранять не допускается).  

__Запуск приложения__  
`java -jar aqa-shop.jar`, приложение запустится на `http://localhost:8080/`  

__Запуск БД__  
`docker-compose up`, к приложению подключается PostgreSQL  
URI и данные для подключения в файле `application.properties`  

### Запуск тестов
1. Запустить БД:
```
docker-compose up -d
```
2. Запустить Приложение:
```
java -jar artifacts/aqa-shop.jar &
```
3. Запустить тесты:
```
gradlew clean test
```
