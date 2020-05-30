package com.example.app2241.apiRest;

import com.example.app2241.model.Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiAdapter {

    private NewsApi newsApi;
    private static RestApiAdapter instance = null;

    private RestApiAdapter(Gson gson) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.BASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        newsApi = retrofit.create(NewsApi.class);
    }

    public static RestApiAdapter getInstance() {

        if (instance == null) {
            instance = new RestApiAdapter(BuildGsonDeserializerArticle());
        }
        return instance;
    }

    public static Gson BuildGsonDeserializerArticle() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Model.class, new ArticleDeserializer());
        return gsonBuilder.create();
    }

    public NewsApi getNewsApi() {
        return newsApi;
    }

}
