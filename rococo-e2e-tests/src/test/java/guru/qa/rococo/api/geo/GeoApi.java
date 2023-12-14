package guru.qa.rococo.api.geo;

import guru.qa.rococo.model.GeoJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface GeoApi {

    @GET("/geo")
    Call<List<GeoJson>> findAll();

    @POST("/geo/findByIds")
    Call<List<GeoJson>> findByIds(@Body List<String> ids);
}
