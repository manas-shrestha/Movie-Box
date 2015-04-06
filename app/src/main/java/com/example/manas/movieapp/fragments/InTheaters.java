package com.example.manas.movieapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.manas.movieapp.API;
import com.example.manas.movieapp.Adapters.HomeFragmentRCVAdapter;
import com.example.manas.movieapp.Info.MostPopular;
import com.example.manas.movieapp.Info.MovieInfo;
import com.example.manas.movieapp.R;
import com.example.manas.movieapp.interfaces.ChangeToolbarTitle;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Manas on 3/30/2015.
 */
public class InTheaters extends Fragment implements ChangeToolbarTitle {


    RecyclerView recyclerView;
    RelativeLayout linearLayout;
    SmoothProgressBar smoothProgressDrawable;
    String url = "https://api.themoviedb.org/3";
    HomeFragmentRCVAdapter homeFragmentRCVAdapter;
    ChangeToolbarTitle changeToolbarTitle;
    int pageno = 1;

    public InTheaters() {
        requestData();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        changeToolbarTitle = (ChangeToolbarTitle) activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        linearLayout = (RelativeLayout) v.findViewById(R.id.linearlayout);
        smoothProgressDrawable = (SmoothProgressBar) v.findViewById(R.id.smoothProgressBar);

        homeFragmentRCVAdapter = new HomeFragmentRCVAdapter(getActivity());
        recyclerView.setAdapter(homeFragmentRCVAdapter);
        changeToolbarTitle.changeTitle("In Theaters");
        return v;
    }

    private void requestData() {

        final RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(url)
                .build();
        API api = adapter.create(API.class);

        api.getInTheaters(new Callback<MostPopular>() {
            @Override
            public void success(MostPopular mostPopular, Response response) {
                Log.e("Most Popular", mostPopular.results.get(0).original_title);
                smoothProgressDrawable.setVisibility(View.INVISIBLE);
                homeFragmentRCVAdapter.setMostPopularObject(mostPopular);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    @Override
    public void changeTitle(String title) {

    }


}
