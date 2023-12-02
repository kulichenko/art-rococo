package guru.qa.rococo.page;

public class ArtistPage extends BasePage<ArtistPage> {
    public static final String URL = CFG.baseUrl() + "/artist";

    @Override
    public ArtistPage waitForPageLoaded() {
        return null;
    }
}
