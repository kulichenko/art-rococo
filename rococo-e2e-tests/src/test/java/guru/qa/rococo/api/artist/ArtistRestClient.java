package guru.qa.rococo.api.artist;

import guru.qa.rococo.api.RestService;
import guru.qa.rococo.model.ArtistJson;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public class ArtistRestClient extends RestService {
    public ArtistRestClient() {
        super(CFG.artistUrl());
    }

    private final ArtistApi api = retrofit.create(ArtistApi.class);

    @Step("Send REST GET('artist') request to artist service")
    @Nullable
    public List<ArtistJson> findAll(Pageable pageable) throws Exception {
        return api.findAll(pageable)
                .execute()
                .body();
    }

    @Step("Send REST GET('artist') request to artist service")
    @Nullable
    public List<ArtistJson> findByTitle(@Nonnull String title) throws Exception {
        return api.findByTitle(title)
                .execute()
                .body();
    }

    @Step("Send REST GET('artist/{id}') request to artist service")
    @Nullable
    public ArtistJson findById(@Nonnull UUID id) throws Exception {
        return api.findById(id)
                .execute()
                .body();
    }

    @Step("Send REST POST('artist') request to artist service")
    @Nullable
    public ArtistJson createArtist(@Nonnull ArtistJson artistJson) throws Exception {
        return api.createArtist(artistJson)
                .execute()
                .body();
    }

    @Step("Send REST PATCH('artist') request to artist service")
    @Nullable
    public ArtistJson editArtist(@Nonnull ArtistJson artistJson) throws Exception {
        return api.editArtist(artistJson)
                .execute()
                .body();
    }
}
