package com.example.manas.movieapp;

import android.app.Dialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import com.example.manas.movieapp.fragments.InTheaters;
import com.example.manas.movieapp.fragments.MostPopularMovies;
import com.example.manas.movieapp.fragments.MostVotes;
import com.example.manas.movieapp.fragments.NavigationDrawerFragmentHandler;
import com.example.manas.movieapp.fragments.Search;
import com.example.manas.movieapp.fragments.SingleMovie;
import com.example.manas.movieapp.fragments.UpComingMovies;
import com.example.manas.movieapp.interfaces.ChangeToolbarTitle;
import com.example.manas.movieapp.interfaces.CloseDrawer;

import java.util.List;


public class MainActivity extends ActionBarActivity implements ChangeToolbarTitle, CloseDrawer, SearchView.OnQueryTextListener {
    DrawerLayout drawerlayout;
    ActionBarDrawerToggle dtoggle;
    Toolbar toolbar;
    int selected = 0;
    SharedPreferences movie;
    String filename = "movie";
    android.support.v7.widget.SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeEveryThing();
    }

    /**
     * @Author -Manas
     * this method will initialize the required elements.
     */
    public void initializeEveryThing() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(8);

        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dtoggle = new ActionBarDrawerToggle(this, drawerlayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerlayout.setDrawerListener(dtoggle);
        drawerlayout.post(new Runnable() {
            @Override
            public void run() {
                dtoggle.syncState();
            }
        });

        movie = getSharedPreferences(filename, 1);
        selected = movie.getInt("position", 0);

        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmenttransaction = fragmentmanager.beginTransaction();

        if (selected == 0) {
            MostPopularMovies mostPopularMovies = new MostPopularMovies();
            fragmenttransaction.replace(R.id.fragment_for_homepage, mostPopularMovies);
        } else if (selected == 1) {
            MostVotes mostVotes = new MostVotes();
            fragmenttransaction.replace(R.id.fragment_for_homepage, mostVotes);

        } else if (selected == 2) {
            InTheaters inTheaters = new InTheaters();
            fragmenttransaction.replace(R.id.fragment_for_homepage, inTheaters);

        } else if (selected == 3) {
            UpComingMovies upComingMovies = new UpComingMovies();
            fragmenttransaction.replace(R.id.fragment_for_homepage, upComingMovies);
        }
        fragmenttransaction.replace(R.id.fragment_for_drawer, new NavigationDrawerFragmentHandler());
        fragmenttransaction.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main2, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        mSearchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        setupSearchView(searchItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.search) {
//            Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_LONG).show();
//            CreateDialog();
//
//        }

        if (dtoggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtoggle.onConfigurationChanged(newConfig);
    }


    public void startSingleMovieViewActivity(Bundle bundle, ViewGroup V) {
        Intent intent = new Intent(MainActivity.this, SingleMovie.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void changeTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void closeDrawer() {
        drawerlayout.closeDrawer(Gravity.START);
    }

    public void CreateDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_layout);
        ImageButton imageButton = (ImageButton) dialog.findViewById(R.id.searchButton);
        final EditText editText = (EditText) dialog.findViewById(R.id.searchET);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmanager = getSupportFragmentManager();
                FragmentTransaction fragmenttransaction = fragmentmanager.beginTransaction();
                Search upComingMovies = new Search(editText.getText().toString());
                fragmenttransaction.replace(R.id.fragment_for_homepage, upComingMovies);
                fragmenttransaction.commit();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void setupSearchView(MenuItem searchItem) {

        if (isAlwaysExpanded()) {
            mSearchView.setIconifiedByDefault(false);
        } else {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        if (searchManager != null) {
//            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
//
//            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
//            for (SearchableInfo inf : searchables) {
//                if (inf.getSuggestAuthority() != null
//                        && inf.getSuggestAuthority().startsWith("applications")) {
//                    info = inf;
//                }
//            }
//            mSearchView.setSearchableInfo(info);
//        }

        mSearchView.setOnQueryTextListener(this);
    }

    public boolean onQueryTextChange(String newText) {
        Log.e("Query = " + newText, "Query = " + newText);
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        Log.e("Query = " + query + " : submitted", "Query = " + query + " : submitted");
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmenttransaction = fragmentmanager.beginTransaction();
        Search upComingMovies = new Search(query);
        fragmenttransaction.replace(R.id.fragment_for_homepage, upComingMovies);
        fragmenttransaction.commit();
        return false;
    }

    public boolean onClose() {

        return false;
    }

    protected boolean isAlwaysExpanded() {
        return false;
    }
}





