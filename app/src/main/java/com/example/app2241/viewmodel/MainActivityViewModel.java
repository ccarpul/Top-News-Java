package com.example.app2241.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app2241.model.apiRest.OnResponse;
import com.example.app2241.model.Model;
import com.example.app2241.model.repository.ArticlesRepository;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<Model> articleLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> totalResults = new MutableLiveData<>();

    //call from WEB service API Rest
    public void queryRepoApiViewModel(int pag, int pagSize, String keyWord, String country,
                                      String category, String source, String language) {

        new ArticlesRepository(pag, pagSize, keyWord, country, category,
                source, language, new OnResponse() {
            @Override
            public void Articles(Model data) {
                totalResults.setValue(data.getTotalResults());
                articleLiveData.setValue(data);
            }
        });
    }

    //TODO call from local data or another datasource
    public void queryRepoLocalDataViewModel() {
        new ArticlesRepository();
    }

    public LiveData<Model> getArticle() {
        return articleLiveData;
    }

    public LiveData<Integer> getTotalResults() {
        return totalResults;
    }

}
