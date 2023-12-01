# Rococo

Проект, построенный на микросервисной архитектуре

```posh
$ bash localenv.sh
```

```posh
$ cd ./rococo-client
$ npm i
$ npm run dev
```

# Что будет являться готовым дипломом?

Тут все просто, диплом глобально требует от тебя реализовать три вещи:

- Реализовать бэкенд на микросервисах (Spring boot, но если вдруг есть желание использовать что-то другое - мы не против)
- Реализовать полноценное покрытие тестами микросервисов и frontend (если будут какие-то
  unit-тесты - это большой плюс!)
- Красиво оформить репозиторий на гихабе, что бы любой, кто зайдет на твою страничку, смог понять,
  как все запустить, как прогнать тесты. Удели внимание этому пункту. Если я не смогу все запустить по твоему README - диплом останется без проверки

# С чего начать?

Мы подготовили для тебя полностью рабочий frontend, а так же страницы регистрации и логина. Кроме
того, у тебя есть и простой бэкенд - по сути своей, мок. В этом бекенде есть контроллеры, по которым
можно понять, какие микросервисы тебе предстоит реализовать. И самое главное - у тебя есть проект
niffler, который будет выступать образцом для подражания в разработке микросервисов. Тестовое
покрытие niffler, однако, является достаточно слабым - учтите это при написании тестов на Rococ - это,
все-таки, диплом для SDET / Senior QA Automation и падать в грязь лицом с десятком тестов на весь сервис
точно не стоит. Итак, приступим!

#### 1. Запусти фронт Rococ, для этого перейти в соответсвующий каталог

```posh
Dmitriis-MacBook-Pro rococo % cd rococo-client


#### 2. Обнови зависимости и запускай фронт:

```posh
Dmitriis-MacBook-Pro rococo-client % npm i
Dmitriis-MacBook-Pro rococo-client % npm run dev
```

Фронт стартанет в твоем браузере на порту 3000: http://127.0.0.1:3000/
Обрати внимание! Надо использовать именно 127.0.0.1, а не localhost

#### 3. Запустите бэкенд rococo-api

```posh
Dmitriis-MacBook-Pro rococo % ./gradlew :rococo-api:bootRun --args='--spring.profiles.active=local'
```

Бэк стартанет на порту 8080: http://127.0.0.1:8080/

# Что дальше?

#### 1. В первую очередь, необходимо реализовать сервис rococo-auth

Фронтенд полностью готов к использованию сервиса auth на порту 9000,
твоя задача взять сервис niffler-auth и аккуратно переделать его для работы с rococo.
Страницы логина / регистрации, а так же стили и графику мы даем:

- deer-logo.svg
- favicon.ico
- styles.css
- login.html
- register.html

Основная задача - аккуратно заменить упоминания о niffler в этом сервисе, а в идеале - еще и
разобраться, как он работает. В этом будет полезно
видео
[Implementing an OAuth 2 authorization server with Spring Security - the new way! by Laurentiu Spilca](https://youtu.be/DaUGKnA7aro)
[Full Stack OAuth 2 - With Spring Security / React / Angular Part 1](https://youtu.be/SfNIjS_2H4M)
[Full Stack OAuth 2 - With Spring Security / React / Angular Part 2](https://youtu.be/3bGer6-6mdY)

#### 2. Как только у вас появилось уже 2 сервиса, есть смысл подумать о докеризации

Чем раньше у ваc получится запустить в докере фронт и все бэкенды, тем проще будет дальше.
На самом деле, докеризация не является строго обязательным требованием, но если вы хотите в будущем
задеплоить свой сервис на прод, прикрутить CI/CD, без этого никак не обойдется

#### 3. Подумать о необходимых микросервисах.

У вас должен остаться основной бэкенд (rococ-api), куда будет ходить фронт, но он будет играть роль прокси,
проверяющего вашу аутентифкацию. Все, как и в niffler. Это значит, что основная логика уйдет в свои
микросервисы со своими БД. На мой вззгляд, здесь будут уместны сервисы rococo-artist,
rococo-painting, rococo-museum, rococo-userdata. Возможно, у вас другие мысли, какие микросервисы создать - вы
можете проявить свою фантазию

#### 4. Выбрать протокол взаимодействия между сервисами

В поставляемом фронтенде классический REST. Его можно поменять на GraphQL - но это потребует
переписывания фронта, и тебе придется делать это самому. Поэтому я бы посоветовал оставить между
фронтом и rococo-gateway старый добрый REST. А вот взаимодействие между микросервисами можно
делать как угодно! REST, gRPC, SOAP. Делай проект я, однозначно взял бы gRPC - не писать руками кучу
model-классов, получить перформанс и простое написание тестов. Стоит сказать, что здесь не
понадобятся streaming rpc, и все ограничится простыми унарными запросами. Однако если вы хотите
использовать REST или SOAP - мы не будем возражать.

#### 5. Реализовать микросервисный backend

Это место где, внезапно, СОВА НАРИСОВАНА! :)
На самом деле, концептуально и технически каждый сервис будет похож на что-то из niffler, поэтому
главное внимательность и аккуратность. Любые отхождения от niffler возможны - ты можешь захотеть
использовать, например, NoSQL базы или по другому организовать конфигурацию / структуру проекта -
никаких ограничений, лишь бы сервис выполнял свое прямое назначение

##### Особенности реализации backend

###### Сервис gateway

1) Pageble контроллеры;
Пример:
```java
  @GetMapping()
  public Page<ArtistJson> getAll(@RequestParam(required = false) String name,
                                 @PageableDefault Pageable pageable) {
    return artistService.getAll(name, pageable);
  }
```
Здесь объект `Pageable` - приходит в виде GET параметров с фронта. 
Так же GET парметром может прийти (а может и нет) параметр name. Тогда запрос в БД должен включать фильтрацию по полю name (`ContainsIgnoreCase`)
Пример репозитория с запросом к БД с учетом Pageable и name
```java
public interface ArtistRepository extends JpaRepository<ArtistEntity, UUID> {

  @Nonnull
  Page<ArtistEntity> findAllByNameContainsIgnoreCase(
          @Nonnull String name,
          @Nonnull Pageable pageable
  );
}
```
Почитать, дополнительно, тут: https://www.baeldung.com/spring-data-jpa-pagination-sorting


2) необходим доступ без авторизации к эндпойнту `/api/session` без необходимости быть
аторизованным, для этого пропишем его в security config:
```java
@EnableWebSecurity
@Configuration
@Profile("!local")
public class SecurityConfigMain {

    private final CorsCustomizer corsCustomizer;

    @Autowired
    public SecurityConfigMain(CorsCustomizer corsCustomizer) {
        this.corsCustomizer = corsCustomizer;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        corsCustomizer.corsCustomizer(http);

        http.authorizeHttpRequests(customizer ->
                customizer.requestMatchers(
                                antMatcher(HttpMethod.GET, "/api/session"),
                                antMatcher(HttpMethod.GET, "/api/artist/**"),
                                antMatcher(HttpMethod.GET, "/api/museum/**"),
                                antMatcher(HttpMethod.GET, "/api/painting/**"))
                        .permitAll()
                        .anyRequest()
                        .authenticated()
        ).oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
```
Все прочие эндпойнты должны требовать авторизацию

#### 6. Подготовить структуру тестового "фреймворка", подумать о том какие прекондишены и как вы будете создавать

Здесь однозначно понадобится возможность API-логина и работы со всеми возможными preconditions проекта - картинами,
художниками, музеями. 

#### 7. Реализовать достаточное, на твой взгляд, покрытие e-2-e тестами

На наш взгляд, только основны позитивных сценариев тут не менее трех десятков.

#### 8. Оформить все красиво!

Да, тут еще раз намекну про важность ридми, важность нарисовать топологию (схему) твоих сервисов, важность скриншотиков и прочих красот












