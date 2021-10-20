package com.oyelabs.marvel.universe;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.oyelabs.marvel.universe.adapter.MainAdapter;
import com.oyelabs.marvel.universe.model.CharacterModel;
import com.oyelabs.marvel.universe.networking.ApiNetworking;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<CharacterModel> characterModelList;
    MainAdapter mainAdapter;
    RecyclerView recyclerView;

    ApiNetworking networking;

    NestedScrollView nestedScrollView;
    FloatingActionButton floatingActionButton;
    ProgressBar progressBar;

    int page = 0;
    int pageLimit = 1559;

    boolean isSearching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nestedScrollView = findViewById(R.id.nested_scroll_view);
        floatingActionButton = findViewById(R.id.fab_button);
        floatingActionButton.setVisibility(View.INVISIBLE);
        progressBar = findViewById(R.id.pg_bar);







        characterModelList = new ArrayList<>();
        recyclerView = findViewById(R.id.grid_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));


        networking = new ApiNetworking(this, characterModelList, mainAdapter, recyclerView, progressBar);

        networking.getData(page, pageLimit);

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                if(!isSearching){
                    progressBar.setVisibility(View.VISIBLE);
                    page = page + 10;
                    networking.getData(page, pageLimit);
                }else{
                    progressBar.setVisibility(View.GONE);
                }



            }
        });
        //show/hide floating action button
        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = nestedScrollView.getScrollY();
            if (scrollY > 50) {
                floatingActionButton.setVisibility(View.VISIBLE);

            } else {
                floatingActionButton.setVisibility(View.GONE);
            }
        });
        //go to top of screen
        floatingActionButton.setOnClickListener(v -> nestedScrollView.fullScroll(ScrollView.FOCUS_UP));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_icon);
        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                characterModelList.clear();
                networking.updateView();
                isSearching = true;
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                characterModelList.clear();
                page=0;
                networking.updateView();
                networking.getData(page, pageLimit);
                isSearching = false;
                progressBar.setVisibility(View.VISIBLE);
                return true;
            }
        });

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                networking.getSearchData(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}