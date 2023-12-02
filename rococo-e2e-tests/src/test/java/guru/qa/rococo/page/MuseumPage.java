package guru.qa.rococo.page;

public class MuseumPage extends BasePage<MuseumPage> {

    public static final String URL = CFG.baseUrl() + "/museum";
    @Override
    public MuseumPage waitForPageLoaded() {
        return null;
    }
}
