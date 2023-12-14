package guru.qa.rococo.api.painting;

import guru.qa.rococo.model.PaintingJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.UUID;

public interface PaintingApi {

    @GET("/painting")
    Call<List<PaintingJson>> findAll();

    @GET("/painting")
    Call<List<PaintingJson>> findByTitle(@Query("title") String title);

    @GET("/painting/{id}")
    Call<PaintingJson> findById(@Path("id") UUID id);

    @GET("/painting/author/{id}")
    Call<List<PaintingJson>> findByAuthor(@Path("id") UUID id);

    @POST("/painting")
    Call<PaintingJson> createPainting(@Body PaintingJson paintingJson);

    @PATCH("/painting")
    Call<PaintingJson> editPainting(@Body PaintingJson paintingJson);

}
