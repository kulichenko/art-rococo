package guru.qa.rococo.api.painting;

import guru.qa.rococo.api.RestService;
import guru.qa.rococo.model.PaintingJson;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class PaintingRestClient extends RestService {
    public PaintingRestClient() {
        super(CFG.picturesUrl());
    }

    private final PaintingApi api = retrofit.create(PaintingApi.class);

    @Step("Send REST GET('/painting') request to painting service")
    @Nullable
    public List<PaintingJson> findAll() throws Exception {
        return api.findAll()
                .execute()
                .body();
    }

    @Step("Send REST GET('/painting') request to painting service")
    @Nullable
    public List<PaintingJson> findByTitle(@Nonnull String title) throws Exception {
        return api.findByTitle(title)
                .execute()
                .body();
    }

    @Step("Send REST GET('/painting/{id}') request to painting service")
    @Nullable
    public PaintingJson findById(@Nonnull UUID id) throws Exception {
        return api.findById(id)
                .execute()
                .body();
    }

    @Step("Send REST GET('/painting/author/{id}') request to painting service")
    @Nullable
    public List<PaintingJson> findByAuthor(@Nonnull UUID id) throws Exception {
        return api.findByAuthor(id)
                .execute()
                .body();
    }

    @Step("Send REST POST('/painting') request to painting service")
    @Nullable
    public PaintingJson createPainting(@Nonnull PaintingJson paintingJson) throws Exception {
        return api.createPainting(paintingJson)
                .execute()
                .body();
    }

    @Step("Send REST PATCH('/painting') request to painting service")
    @Nullable
    public PaintingJson editPainting(@Nonnull PaintingJson paintingJson) throws Exception {
        return api.editPainting(paintingJson)
                .execute()
                .body();
    }
}
