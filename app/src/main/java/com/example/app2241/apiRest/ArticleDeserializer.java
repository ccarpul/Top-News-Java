package com.example.app2241.apiRest;

import android.util.Log;

import com.example.app2241.model.Elements;
import com.example.app2241.model.Model;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ArticleDeserializer implements JsonDeserializer<Model> {
    @Override
    public Model deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                            throws JsonParseException {
        Gson gson = new Gson();
        Model model = gson.fromJson(json, Model.class);
        JsonArray articlesResponseData = json.getAsJsonObject().getAsJsonArray("articles");
        JsonPrimitive totalResults = json.getAsJsonObject().getAsJsonPrimitive("totalResults");
        int total = totalResults.getAsInt();
        model = new Model(deserializeArticlesJson(articlesResponseData), total);
        //model.setTotalResults(total);
        return model;
    }

    private ArrayList<Elements> deserializeArticlesJson(JsonArray articlesResponseData){

        ArrayList<Elements> arrayListresponse = new ArrayList<>();

        String idValue, nameValue, author="", title="", description="", url="", urlToImage="", publishedAt="", content="";
        for (int i = 0; i < articlesResponseData.size(); i++) {

            JsonObject responseArticlesObject = articlesResponseData.get(i).getAsJsonObject();
            JsonObject source = responseArticlesObject.getAsJsonObject("source");
            if (!source.get("id").isJsonNull()) {
                idValue = source.get("id").getAsString();
            } else {
                idValue = "No Data";
            }
            if (!source.get("name").isJsonNull()) {
                nameValue = source.get("name").getAsString();
            } else {
                nameValue = "No Data";
            }

            if (!responseArticlesObject.get("author").isJsonNull()) {
                author = responseArticlesObject.get("author").getAsString();
            } else {
                author = "No Data";
            }

            if (!responseArticlesObject.get("title").isJsonNull()) {
                title = responseArticlesObject.get("title").getAsString();
            } else {
                title = "No Data";
            }


            if (!responseArticlesObject.get("description").isJsonNull()) {
                description = responseArticlesObject.get("description").getAsString();
            } else {
                description = "No Data";
            }


            if (!responseArticlesObject.get("url").isJsonNull()) {
                url = responseArticlesObject.get("url").getAsString();
            } else {
                url = "No Data";
            }


            if (!responseArticlesObject.get("urlToImage").isJsonNull()) {
                urlToImage = responseArticlesObject.get("urlToImage").getAsString();
            } else {
                urlToImage = "No Data";
            }


            if (!responseArticlesObject.get("publishedAt").isJsonNull()) {
                publishedAt = responseArticlesObject.get("publishedAt").getAsString();
            } else {
                publishedAt = "No Data";
            }

            if (!responseArticlesObject.get("content").isJsonNull()) {
                content = responseArticlesObject.get("content").getAsString();
            } else {
                content = "No Data";
            }


            Elements  currentElements = new Elements(idValue, nameValue, author, title, description,
                                                        url, urlToImage, publishedAt, content);
            arrayListresponse.add(currentElements);
        }
        return arrayListresponse;
    }
}
