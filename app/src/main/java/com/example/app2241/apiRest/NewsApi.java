package com.example.app2241.apiRest;
import com.example.app2241.model.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET(ConstantUrl.ENDPOINT + ConstantUrl.APIKEY)
    Call<Model> getData(@Query("page") int page
            , @Query("pagesize") int page_size
            , @Query("q") String key_word
            , @Query("country") String country
            , @Query("category") String category
            , @Query("sources") String source
            , @Query("language") String language);
}
