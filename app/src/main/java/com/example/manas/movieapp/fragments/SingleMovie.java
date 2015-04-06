package com.example.manas.movieapp.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.manas.movieapp.API;
import com.example.manas.movieapp.Adapters.CastAdapter;
import com.example.manas.movieapp.Info.SinlgeMovie;
import com.example.manas.movieapp.R;
import com.example.manas.movieapp.utils.DatabaseHelper;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import com.squareup.picasso.Picasso;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Manas on 3/23/2015.
 */
public class SingleMovie extends ActionBarActivity {
    String id;
    String path = "https://www.youtube.com/watch?v=2bqh-UCY6Zg";
    SmoothProgressBar smoothProgressBar;
    TextView nameTV, synopsisTV, budgetTV, languageTV, revenueTV;
    ImageView backdropIV, posterIV;
    RatingBar ratingRB;
    ObservableScrollView scrollView;
    android.support.v7.widget.Toolbar toolbar;
    FloatingActionButton floatingActionButton;

    ListView cast;
    CastAdapter castAdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_movie);
        Bundle b = getIntent().getExtras();

        id = b.getString("id");
        Log.e("ID", id);


        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.appbar_single_movie);
        ratingRB = (RatingBar) findViewById(R.id.singleMovieRating);
        backdropIV = (ImageView) findViewById(R.id.backdropiv);
        scrollView = (ObservableScrollView) findViewById(R.id.scrollView);
        nameTV = (TextView) findViewById(R.id.nameTV);
        synopsisTV = (TextView) findViewById(R.id.synopsisTV);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.attachToScrollView(scrollView);

        posterIV = (ImageView) findViewById(R.id.posterIV);
        languageTV = (TextView) findViewById(R.id.languageTV);
        revenueTV = (TextView) findViewById(R.id.revenueTV);
        budgetTV = (TextView) findViewById(R.id.budgetTV);
        cast = (ListView) findViewById(R.id.castLV);
        smoothProgressBar = (SmoothProgressBar) findViewById(R.id.smoothProgressBar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(8);
//
//        setActionBarColor();

        requestData();
//
//
//
        backdropIV.setImageResource(R.drawable.thetourist);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                final int headerHeight = findViewById(R.id.backdropiv).getHeight() - 100;
                final float ratio = (float) Math.min(Math.max(scrollY, 0), headerHeight) / headerHeight;
                final int newAlpha = (int) (ratio * 255);
                toolbar.getBackground().setAlpha(newAlpha);
            }
        });


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
//            NavUtils.navigateUpFromSameTask(this);
            onBackPressed();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void requestData() {
        String url = "https://api.themoviedb.org/3";
        ;
        final RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(url)
                .build();
        API api = adapter.create(API.class);

        api.getSingle(id, new Callback<SinlgeMovie>() {
            @Override
            public void success(SinlgeMovie singleMovie, Response response) {

                Picasso.with(SingleMovie.this).load("http://image.tmdb.org/t/p/w500" + singleMovie.backdrop_path).error(R.drawable.picturenotavailable).placeholder(R.drawable.loading).into(backdropIV, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        setActionBarColor(backdropIV.getDrawable());
                    }

                    @Override
                    public void onError() {

                    }
                });
                Picasso.with(SingleMovie.this).load("http://image.tmdb.org/t/p/w500" + singleMovie.poster_path).into(posterIV, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        posterIV.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }

                    @Override
                    public void onError() {

                    }
                });

                setEveryThing(singleMovie);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Error", error.toString());
            }
        });

    }

    private void setActionBarColor(Drawable drawable) {
        //change colors of the stars in RatingBar
        LayerDrawable stars = (LayerDrawable) ratingRB.getProgressDrawable();
        stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

                int darkVibrantColor = palette.getDarkVibrantColor(222345);
                int lightVibrantColor = palette.getLightVibrantColor(222345);
                int darkMutedColor = palette.getDarkMutedColor(222345);
                int lightMutedColor = palette.getLightMutedColor(222345);
                int mutedColor = palette.getMutedColor(222345);
                int vibrantColor = palette.getVibrantColor(222345);
                toolbar.setBackgroundColor(vibrantColor);
                toolbar.getBackground().setAlpha(0);

                //change floatingactionbar color according to palette
                if (palette.getDarkVibrantColor(222345) == Color.WHITE) {
                    Log.e("SAME SAME", "SAME YO");
                }
                floatingActionButton.setColorPressed(darkVibrantColor);
                floatingActionButton.setColorNormal(vibrantColor);


            }
        });
    }

    private void setEveryThing(SinlgeMovie singleMovie) {
        scrollView.setVisibility(View.VISIBLE);
        nameTV.setText(singleMovie.title);
        castAdater = new CastAdapter(getLayoutInflater(), SingleMovie.this, singleMovie);
        cast.setAdapter(castAdater);
        getSupportActionBar().setTitle(singleMovie.title);
        synopsisTV.setText(singleMovie.overview);
        ratingRB.setRating(Float.parseFloat(singleMovie.vote_average) / 2);
        budgetTV.setText(singleMovie.budget);
        languageTV.setText(singleMovie.original_language);
        revenueTV.setText(singleMovie.revenue);

        smoothProgressBar.setVisibility(View.INVISIBLE);

    }
}
