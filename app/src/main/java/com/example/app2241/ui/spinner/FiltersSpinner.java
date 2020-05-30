package com.example.app2241.ui.spinner;

public class FiltersSpinner {

    private String textview_filter;
    private int image_filter;

    public FiltersSpinner(String textview_filter, int image_filter) {
        this.textview_filter = textview_filter;
        this.image_filter = image_filter;
    }

    public String getTextview_filter() {
        return textview_filter;
    }

    public int getImage_filter() {
        return image_filter;
    }
}