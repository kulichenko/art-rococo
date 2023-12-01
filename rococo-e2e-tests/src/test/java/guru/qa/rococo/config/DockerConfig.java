package guru.qa.rococo.config;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

public class DockerConfig implements Config {

    //package access для того, чтобы исключить возможность создания конфига из других покетов
    static final DockerConfig config = new DockerConfig();

    private DockerConfig() {
    }

    static {
        Configuration.browserSize = "1920x1200";
        Configuration.remote = "http://selenoid:4444/wd/hub";
        Configuration.timeout = 10000;
        Configuration.browser = "chrome";
        Configuration.browserVersion = "117.0";
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--no-sandbox");
    }

    @Override
    public String databaseAddress() {
        return "rococo-all-db:5432";
    }

    @Override
    public String baseUrl() {
        return "http://rococo.dc";
    }

    @Override
    public String authUrl() {
        return "http://auth.rococo.dc:9000";
    }

    @Override
    public String geoUrl() {
        return "http://geo.rococo:8083";
    }

    @Override
    public String userDataUrl() {
        return "http://geo.rococo:8087";
    }

    @Override
    public String artistUrl() {
        return "http://geo.rococo:8086";
    }

    @Override
    public String museumUrl() {
        return "http://geo.rococo:8085";
    }

    @Override
    public String picturesUrl() {
        return "http://geo.rococo:8084";
    }

    @Override
    public String apiUrl() {
        return "http://geo.rococo:8080";
    }

    @Override
    public String kafkaAddress() {
        return "kafka:9092";
    }
}
