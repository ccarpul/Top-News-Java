package com.example.app2241.model.repository;

import android.util.Log;

import com.example.app2241.model.apiRest.service.NewsApi;
import com.example.app2241.model.apiRest.OnResponse;
import com.example.app2241.model.apiRest.RestApiAdapter;
import com.example.app2241.model.Elements;
import com.example.app2241.model.Model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ArticlesRepository {

    private NewsApi newsApi;
    private Model data;
    private Call<Model> call;

    public ArticlesRepository() {
        //TODO método para extraer datos desde otra fuente de datos
        ArrayList<Elements> localData = new ArrayList<>();
        //data= new Model (localData);
    }

    public ArticlesRepository(int pag, int pagSize, String keyWord, String country
            , String category, String source, String language, final OnResponse onResponse) {
        newsApi = RestApiAdapter.getInstance().getNewsApi();
        call = newsApi.getData(pag, pagSize, keyWord, country, category, source, language);
        call.enqueue(new Callback<Model>() {

            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                data = response.body();
                onResponse.Articles(data);
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

                Log.i(TAG, "onFailure: " + t);
            }
        });
    }
}
