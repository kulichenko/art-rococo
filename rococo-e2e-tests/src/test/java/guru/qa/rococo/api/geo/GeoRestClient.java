package guru.qa.rococo.api.geo;

import guru.qa.rococo.api.RestService;
import guru.qa.rococo.model.CountryJson;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class GeoRestClient extends RestService {
    public GeoRestClient() {
        super(CFG.geoUrl());
    }

    private final GeoApi api = retrofit.create(GeoApi.class);

    @Step("Send REST GET('/geo') request to geo service")
    @Nullable
    public List<CountryJson> findAll() throws Exception {
        return api.findAll()
                .execute()
                .body();
    }

    @Step("Send REST POST ('/geo/findByIds') request to geo service")
    @Nullable
    public List<CountryJson> findByIds(@Nonnull List<String> ids) throws Exception {
        return api.findByIds(ids)
                .execute()
                .body();
    }
}
