package com.example.app2241.ui.spinner;

import android.app.Activity;
import android.widget.Spinner;

import com.example.app2241.R;

import java.util.ArrayList;

public class SettingSpinner {

    private Activity activity;

    private FiltersSpinner filtersSpinner;
    public static final int[][] spinner_id_values = new int[][]{

            {R.id.spinner_filter_country, R.array.countries, R.array.countries_values},
            {R.id.spinner_filter_category, R.array.categories, R.array.categories},
            {R.id.spinner_filter_source, R.array.sources, R.array.sources_values},
            {R.id.spinner_filter_language, R.array.languages, R.array.languages_values},
            {R.id.spinner_filter_sortby, R.array.sort_by, R.array.sort_by_values}
    };


    private int[] flag_countries = new int[]{
            R.drawable.language_icon,
            R.drawable.ic_ar,
            R.drawable.ic_br,
            R.drawable.ic_ca,
            R.drawable.ic_de,
            R.drawable.ic_fr,
            R.drawable.ic_gb,
            R.drawable.ic_it,
            R.drawable.ic_mx,
            R.drawable.ic_pt,
            R.drawable.ic_us,
            R.drawable.ic_ve,
    };
    private int[] flag_languages = new int[]{
            R.drawable.language_icon,
            R.drawable.ic_us,
            R.drawable.ic_ve,
            R.drawable.ic_fr,
            R.drawable.ic_it,
            R.drawable.ic_pt,

    };
    private int[] image_sources = new int[]{
            R.drawable.language_icon,
            R.drawable.ic_abc_news,
            R.drawable.ic_bbc_news,
            R.drawable.ic_bbc_news,
            R.drawable.ic_bloomberg,
            R.drawable.ic_cnn,
            R.drawable.ic_cnn,
            R.drawable.ic_espn,
            R.drawable.ic_fox_news,
            R.drawable.ic_fox_news,
            R.drawable.ic_globo,
            R.drawable.ic_google,
    };

    private int[] category = new int[9];

    private int[] sort_by = new int[5];
    ArrayList<int[]> arrayList_image_resourses = new ArrayList();

    public SettingSpinner() {

    }

    public SettingSpinner(Activity activity) {
        this.activity = activity;
        category[0] = R.drawable.language_icon;
        sort_by[3] = R.drawable.date_icon;
        arrayList_image_resourses.add(flag_countries);
        arrayList_image_resourses.add(category);
        arrayList_image_resourses.add(image_sources);
        arrayList_image_resourses.add(flag_languages);
        arrayList_image_resourses.add(sort_by);

        BuilderSpinner();
    }

    public void BuilderSpinner() {

        AdapterSpinnerFilter adapterSpinnerFilter;
        String[] filter;

        for (int i = 0; i < spinner_id_values.length; i++) {

            ArrayList<FiltersSpinner> list_filter_spinner = new ArrayList<>();

            final Spinner spinner_filter = this.activity.findViewById(spinner_id_values[i][0]);
            filter = activity.getResources().getStringArray(spinner_id_values[i][1]);
            int[] image_resources = arrayList_image_resourses.get(i);

            for (int j = 0; j < filter.length; j++) {
                filtersSpinner = new FiltersSpinner(filter[j], image_resources[j]);
                list_filter_spinner.add(filtersSpinner);
            }
            adapterSpinnerFilter = new AdapterSpinnerFilter(activity, list_filter_spinner);
            spinner_filter.setAdapter(adapterSpinnerFilter);

        }
        Spinner spinner = this.activity.findViewById(spinner_id_values[4][0]);
        spinner.setSelection(3);
        spinner = this.activity.findViewById(spinner_id_values[3][0]);
        spinner.setSelection(1);
    }

    public ArrayList getValuesSpinner(Activity activity) {
        String[] filter;
        ArrayList<String> filters = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            Spinner spinner = activity.findViewById(spinner_id_values[i][0]);
            filter = activity.getResources().getStringArray(spinner_id_values[i][2]);

            if (spinner.getSelectedItemPosition() != 0) {

                filter[i] = filter[spinner.getSelectedItemPosition()];
                switch (i) {
                    case 0: //Country
                        filters.add(filter[i]);
                        break;
                    case 1: //Category
                        filters.add(filter[i]);

                        break;
                    case 2: //Source
                        filters.add(filter[i]);

                        break;
                    case 3: //Language
                        filters.add(filter[i]);

                        break;
                    case 4: //Sort By
                        filters.add(filter[i]);

                        break;
                    default:
                        break;
                }

            } else {
                filters.add("");
            }

        }
        return filters;

    }
}
