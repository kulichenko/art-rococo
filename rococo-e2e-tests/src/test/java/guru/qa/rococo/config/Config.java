package guru.qa.rococo.config;

public interface Config {

    String PROJECT_NAME = "rococo";

    static Config getInstance() {
        if ("docker".equals(System.getProperty("test.env"))) {
            return DockerConfig.config;
        }
        return LocalConfig.config;
    }

    int TEN_MB = 10 * 1024 * 1024;

    String frontUrl();

    String databaseAddress();

    default String dataBaseUser() {
        return "postgres";
    }

    default String dataBasePassword() {
        return "secret";
    }

    default int dataBasePort() {
        return 5432;
    }


    String authUrl();

    String geoUrl();

    String userDataUrl();

    String artistUrl();

    String museumUrl();

    String picturesUrl();

    String apiUrl();

    String kafkaAddress();
}
