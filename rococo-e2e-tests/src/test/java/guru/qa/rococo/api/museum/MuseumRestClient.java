package guru.qa.rococo.api.museum;

import guru.qa.rococo.api.RestService;
import guru.qa.rococo.model.MuseumJson;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class MuseumRestClient extends RestService {
    public MuseumRestClient() {
        super(CFG.museumUrl());
    }

    private final MuseumApi api = retrofit.create(MuseumApi.class);

    @Step("Send REST GET('/museum') request to museum service")
    @Nullable
    public List<MuseumJson> findAll() throws Exception {
        return api.findAll()
                .execute()
                .body();
    }

    @Step("Send REST GET('/museum') request to museum service")
    @Nullable
    public List<MuseumJson> findByTitle(@Nonnull String title) throws Exception {
        return api.findByTitle(title)
                .execute()
                .body();
    }

    @Step("Send REST GET('/museum/{id}') request to museum service")
    @Nullable
    public MuseumJson findById(@Nonnull UUID id) throws Exception {
        return api.findById(id)
                .execute()
                .body();
    }

    @Step("Send REST POST('/museum') request to museum service")
    @Nullable
    public MuseumJson createMuseum(@Nonnull MuseumJson museumJson) throws Exception {
        return api.createMuseum(museumJson)
                .execute()
                .body();
    }

    @Step("Send REST PATCH('/museum') request to museum service")
    @Nullable
    public MuseumJson editMuseum(@Nonnull MuseumJson museumJson) throws Exception {
        return api.editMuseum(museumJson)
                .execute()
                .body();
    }
}
