# social-network
## Инструкция по запуску
Следущие комманды запускают приложение
```
git clone https://github.com/daniiloorzhak/social-network.git
cd social-network/
docker compose up -d
./gradlew clean bootRun
```

Удалить контейнер
```
docker compose down
```

## Подробности
* Swagger документация доступна по адресу http://localhost:8080/swagger-ui/index.html
* Приложение запускается на порту 8080
* База данных доступна по порту 8573
