package guru.qa.rococo.api.museum;

import guru.qa.rococo.model.MuseumJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.UUID;

public interface MuseumApi {

    @GET("/museum")
    Call<List<MuseumJson>> findAll();

    @GET("/museum")
    Call<List<MuseumJson>> findByTitle(@Query("title") String title);

    @GET("/museum/{id}")
    Call<MuseumJson> findById(@Path("id") UUID id);

    @POST("/museum")
    Call<MuseumJson> createMuseum(@Body MuseumJson museumJson);

    @PATCH("/museum")
    Call<MuseumJson> editMuseum(@Body MuseumJson museumJson);
}
