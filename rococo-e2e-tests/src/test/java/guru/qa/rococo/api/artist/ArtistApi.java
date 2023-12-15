package guru.qa.rococo.api.artist;


import guru.qa.rococo.model.ArtistJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.UUID;

public interface ArtistApi {

    @GET("/artist")
    Call<List<ArtistJson>> findAll();

    @GET("/artist/{id}")
    Call<ArtistJson> findById(@Path("id") UUID id);


    @GET("/artist")
    Call<List<ArtistJson>> findByName(@Query("name") String name);

    @POST("/artist")
    Call<ArtistJson> createArtist(@Body ArtistJson artistJson);

    @PATCH("/artist")
    Call<ArtistJson> editArtist(@Body ArtistJson artistJson);
}
