package guru.qa.rococo.data;

import guru.qa.rococo.config.Config;
import org.apache.commons.lang3.StringUtils;

public enum DataBase {
    USERDATA("jdbc:postgresql://%s/rococo-userdata"),
    AUTH("jdbc:postgresql://%s/rococo-auth"),
    MUSEUM("jdbc:postgresql://%s/rococo-museum"),
    ARTIST("jdbc:postgresql://%s/rococo-artist"),
    PICTURES("jdbc:postgresql://%s/rococo-pictures"),
    GEO("jdbc:postgresql://%s/rococo-geo");
    private final String url;

    DataBase(String url) {
        this.url = url;
    }

    private static final Config CFG = Config.getInstance();

    public String getUrl() {
        return String.format(url, CFG.databaseAddress());
    }

    public String getUrlForP6Spy() {
        return "jdbc:p6spy:" + StringUtils.substringAfter(getUrl(), "jdbc:");
    }
}
