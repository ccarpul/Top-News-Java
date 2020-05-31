package com.example.app2241.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app2241.R;
import com.example.app2241.model.apiRest.ConstantUrl;
import com.example.app2241.model.Model;
import com.example.app2241.views.ui.AdapterRecycler;
import com.example.app2241.views.ui.spinner.SettingSpinner;
import com.example.app2241.viewmodel.MainActivityViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterRecycler.OnClickItemSelected, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private MainActivityViewModel mainActivityViewModel;
    private int pag = 1, pagSize = ConstantUrl.PAGESIZE, totalResults;
    private String keyWord = ConstantUrl.KEYWORD_INIT, country = ConstantUrl.COUNTRY_INIT;
    private String category = ConstantUrl.CATEGORY_INIT, source = ConstantUrl.SOURCE_INIT;
    private String language = ConstantUrl.LANGUAGE_INIT;
    private AdapterRecycler adapterRecycler;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private EditText editTextKeyWord;
    private Spinner spinnerCountry, spinnerCategory, spinnerSource, spinnerSortBy;
    private NavigationView navigationView;
    private Drawable icon_menu_navigation, iconMenuOverFlow;
    private ArrayList<String> arrayList_values_filters;
    private SettingSpinner settingSpinner;
    private ImageView imageViewButtonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        configObservers();
        SettingEventsRecyclerView();
        SettingsNavigationView();
        mainActivityViewModel.queryRepoApiViewModel(pag, pagSize, keyWord, country, category, source, language);
    }

    private void configObservers() {

        mainActivityViewModel.getTotalResults().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer total) {
                totalResults = total;
            }
        });

        mainActivityViewModel.getArticle().observe(this, new Observer<Model>() {
            @Override
            public void onChanged(Model model) {

                if (model.getData().size() > 0) {

                    adapterRecycler.AdapterRecycler(model.getData());
                } else {
                    Toast.makeText(MainActivity.this, R.string.no_results, Toast.LENGTH_SHORT).show();
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initViews() {

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        editTextKeyWord = findViewById(R.id.edit_key_word);
        spinnerCountry = findViewById(R.id.spinner_filter_country);
        spinnerCategory = findViewById(R.id.spinner_filter_category);
        spinnerSource = findViewById(R.id.spinner_filter_source);
        spinnerSortBy = findViewById(R.id.spinner_filter_sortby);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        adapterRecycler = new AdapterRecycler();
        recyclerView.setAdapter(adapterRecycler);
        progressBar = findViewById(R.id.progressBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        imageViewButtonSearch = findViewById(R.id.button_search);
        imageViewButtonSearch.setOnClickListener(this);
        spinnerSource.setOnItemSelectedListener(this);
        spinnerSortBy.setVisibility(View.INVISIBLE);
        editTextKeyWord.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_search_black, 0);


    }

    private void SettingEventsRecyclerView() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isLastItemDisplaying(recyclerView)) {

                    if (pag * pagSize >= totalResults) {
                        if (totalResults > 5)
                            Toast.makeText(MainActivity.this, R.string.no_more_result, Toast.LENGTH_SHORT).show();

                    } else {
                        pag++;
                        progressBar.setVisibility(View.VISIBLE);
                        mainActivityViewModel.queryRepoApiViewModel(pag, pagSize, keyWord, country, category, source, language);
                    }
                }
            }
        });

        adapterRecycler.setOnClickItemSelected(this);
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {

        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findLastCompletelyVisibleItemPosition();

            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition ==
                    recyclerView.getAdapter().getItemCount() - 1) {
                return true;
            }
        }
        return false;
    }

    private void SettingsNavigationView() {

        icon_menu_navigation = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_menu_nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.about_message, R.string.app_name);
        toggle.syncState();
        toolbar.setNavigationIcon(icon_menu_navigation);

        settingSpinner = new SettingSpinner(this);
    }

    @Override
    public void Onclick(String url) {

        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(getResources().getString(R.string.extras_url), url);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        iconMenuOverFlow = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_menu);
        toolbar.setOverflowIcon(iconMenuOverFlow);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_settings:
                //TODO
                break;

            case R.id.menu_about:

                AlertDialog.Builder alertDialogbuilder = new AlertDialog.Builder(this);
                alertDialogbuilder.setMessage(R.string.about_message)
                        .setCancelable(true)
                        .setPositiveButton(R.string.button_alertdialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog title = alertDialogbuilder.create();
                title.setTitle(R.string.menu_about);
                title.show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        arrayList_values_filters = settingSpinner.getValuesSpinner(this);
        pag = 1;
        keyWord = editTextKeyWord.getText().toString();
        country = arrayList_values_filters.get(0);
        category = arrayList_values_filters.get(1);
        source = arrayList_values_filters.get(2);
        language = arrayList_values_filters.get(3);
        if (arrayList_values_filters.get(0).equals("") && arrayList_values_filters.get(1).equals("") &&
                arrayList_values_filters.get(2).equals("") && arrayList_values_filters.get(3).equals("") &&
                editTextKeyWord.getText().toString().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.no_results), Toast.LENGTH_SHORT).show();
        } else {
            adapterRecycler = new AdapterRecycler();
            recyclerView.setAdapter(adapterRecycler);
            mainActivityViewModel.queryRepoApiViewModel(pag, pagSize, keyWord, country, category, source, language);
            adapterRecycler.setOnClickItemSelected(this);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        arrayList_values_filters = settingSpinner.getValuesSpinner(this);
        if (arrayList_values_filters.get(2).equals("")) {
            spinnerCountry.setVisibility(View.VISIBLE);
            spinnerCategory.setVisibility(View.VISIBLE);
        } else {
            spinnerCountry.setVisibility(View.INVISIBLE);
            spinnerCategory.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
