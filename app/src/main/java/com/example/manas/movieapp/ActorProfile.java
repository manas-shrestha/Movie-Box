package com.example.manas.movieapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.manas.movieapp.Info.ActorInfo;
import com.example.manas.movieapp.Info.SinlgeMovie;
import com.squareup.picasso.Picasso;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Manas on 3/30/2015.
 */
public class ActorProfile extends ActionBarActivity {
    Toolbar toolbar;
    String baseurl = "http://api.themoviedb.org/3";
    String id;
    ImageView actorPhoto;
    SmoothProgressBar smoothProgressBar;
    LinearLayout linearLayout;
    TextView birthplace, dateofbirth, biography, homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actor_profile);
        toolbar = (Toolbar) findViewById(R.id.app_bar_actorprofile);

        Bundle b = getIntent().getExtras();
        this.id = b.getString("id");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(8);
        getSupportActionBar().setTitle("Loading Profile...");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        birthplace = (TextView) findViewById(R.id.birthplaceTV);
        dateofbirth = (TextView) findViewById(R.id.birthdayTV);
        biography = (TextView) findViewById(R.id.biographyTV);
        homepage = (TextView) findViewById(R.id.homepageTV);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayoutprofile);
        smoothProgressBar = (SmoothProgressBar) findViewById(R.id.smoothProgressBar);
        actorPhoto = (ImageView) findViewById(R.id.ivactor);
        requestData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void requestData() {
        final RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(baseurl)
                .build();
        API api = adapter.create(API.class);

        api.getProfile(id, new Callback<ActorInfo>() {
            @Override
            public void success(ActorInfo actorInfo, Response response) {

                Picasso.with(ActorProfile.this).load("http://image.tmdb.org/t/p/w500" + actorInfo.profile_path).error(R.drawable.picturenotavailable).placeholder(R.drawable.loading).into(actorPhoto);

                smoothProgressBar.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                getSupportActionBar().setTitle(actorInfo.name);
                setEverything(actorInfo);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Error", error.toString());
            }
        });

    }

    private void setEverything(ActorInfo actorInfo) {
        birthplace.setText(actorInfo.place_of_birth);
        dateofbirth.setText(actorInfo.birthday);
        biography.setText(actorInfo.biography);
        homepage.setText(actorInfo.homepage);
    }


}

