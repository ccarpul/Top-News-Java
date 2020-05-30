package com.example.app2241.model;

import java.util.ArrayList;

public class Model {

    private ArrayList<Elements> data;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    private int totalResults;

    public ArrayList<Elements> getData() {
        return data;
    }

    public Model(ArrayList<Elements> data, int totalResults) {
        this.data = data;
        this.totalResults = totalResults;
    }

}
