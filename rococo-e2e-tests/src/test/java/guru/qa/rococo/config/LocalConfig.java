package guru.qa.rococo.config;

public class LocalConfig implements Config {

    //package access для того, чтобы исключить возможность создания конфига из других пакетов
    static final LocalConfig config = new LocalConfig();

    private LocalConfig() {
    }

    @Override
    public String databaseAddress() {
        return "127.0.0.1:5432";
    }

    public String baseUrl() {
        return "http://127.0.0.1:3000";
    }

    @Override
    public String authUrl() {
        return "http://127.0.0.1:9000";
    }

    @Override
    public String geoUrl() {
        return "http://127.0.0.1:8083";
    }

    @Override
    public String userDataUrl() {
        return "http://127.0.0.1:8087";
    }

    @Override
    public String artistUrl() {
        return "http://127.0.0.1:8086";
    }

    @Override
    public String museumUrl() {
        return "http://127.0.0.1:8085";
    }

    @Override
    public String picturesUrl() {
        return "http://127.0.0.1:8084";
    }

    @Override
    public String apiUrl() {
        return "http://127.0.0.1:8080";
    }

    @Override
    public String kafkaAddress() {
        return "127.0.0.1:9092";
    }
}
