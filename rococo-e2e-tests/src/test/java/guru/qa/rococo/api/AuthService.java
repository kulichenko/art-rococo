package guru.qa.rococo.api;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthService {

    //init state вход на страницу http://127.0.0.1:3000/
    // 1. Пустые куки
    // 2. Session Storage:
    //      codeChallenge	2yuy0OS6XXsJv3CgxI2NHsQSW34KUOoe5iNh5PdjWIg
    //      codeVerifier	d2Ll5s4dI1OhMEGZpetovfq_qDjNDwYiB5bHdDHqMkY
    // Empty Local Storage


    @GET("/oauth2/authorize")
    Call<Void> authorize(
            @Query("response_type") String responseType,
            @Query("client_id") String clientId,
            @Query("scope") String scope,
            @Query(value = "redirect_uri", encoded = true) String redirectUri,
            @Query("code_challenge") String codeChallenge,
            @Query("code_challenge_method") String codeChallengeMethod
    );

    @POST("/login")
    @FormUrlEncoded
    Call<Void> login(
//            @Header("Cookie") String jsessionIdCookie,
//            @Header("Cookie") String xsrfTockenCookie,
            @Field("username") String username,
            @Field("password") String password,
            @Field("_csrf") String _csrf
    );

    @POST("/oauth2/token")
    Call<JsonNode> token(
            @Header("Authorization") String basicAuthorization,
            @Query("client_id") String clientId,
            @Query(value = "redirect_uri", encoded = true) String redirectUri,
            @Query("grant_type") String grantType,
            @Query("code") String code,
            @Query("code_verifier") String codeVerifier

    );

}
