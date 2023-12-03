### Rococo - микросервисное приложение, которое состоит из нескольких сервисов:

- rococo-client - frontend приложения
- rococo-auth - модуль авторизации
- rococo-api - модуль проксирования запросов от клиента к сервисам приложения
- rococo-artist - модуль управления художниками
- rococo-museum - модуль управления музеями
- rococo-pictures - модуль управления картинами
- rococo-userdata - модуль управления данными пользователя
- rococo-geo - модуль управления странами

### Схема взаимодействия сервисов Rococo

![img_2.png](img_2.png)

## Алгоритм запуска приложения

### 1. Запусти базу данных, kafka и zookeeper необходимо выполнить команду

```posh
$ bash localenv.sh
```

### 2. Создай БД для сервисов

```posh
create database "rococo-auth" with owner postgres;
create database "rococo-artist" with owner postgres;
create database "rococo-geo" with owner postgres;
create database "rococo-museum" with owner postgres;
create database "rococo-pictures" with owner postgres;
create database "rococo-userdata" with owner postgres;
```

### 3. Запусти frontend проекта, для этого нужно перейти в каталог

```posh
$ cd ./rococo-client
```

Обновить зависимости

```posh
$ npm i
```

и запусти фронт с помощью команды

```posh
$ npm run dev
```

Фронт стартанет на порту 3000: http://127.0.0.1:3000/

### 4. Запустите модуль авторизации rococo-auth

При запуске в Intellij Idea предварительно нужно указать Active profile=local
либо запустить с помощью команды в терминале

```posh
./gradlew :rococo-auth:bootRun --args='--spring.profiles.active=local'
```

### 5. Далее в произвольном порядке запусти оставшиеся сервисы

При запуске в Intellij Idea предварительно нужно указать Active profile=local
либо запустить с помощью команды в терминале

```posh
./gradlew :rococo-api:bootRun --args='--spring.profiles.active=local'
./gradlew :rococo-artist:bootRun --args='--spring.profiles.active=local'
./gradlew :rococo-geo:bootRun --args='--spring.profiles.active=local'
./gradlew :rococo-museum:bootRun --args='--spring.profiles.active=local'
./gradlew :rococo-pictures:bootRun --args='--spring.profiles.active=local'
./gradlew :rococo-userdata:bootRun --args='--spring.profiles.active=local'
```












