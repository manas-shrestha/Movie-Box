package com.example.manas.movieapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.manas.movieapp.Info.SinlgeMovie;
import com.example.manas.movieapp.R;
import com.example.manas.movieapp.interfaces.CloseDrawer;

import org.parceler.transfuse.annotations.OnSaveInstanceState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Manas on 3/24/2015.
 */
public class NavigationDrawerFragmentHandler extends Fragment implements CloseDrawer {
    ListView sidelist;
    List<String> itemlist = new ArrayList<>();
    int mPosition = -1; //will be used to highlight selected.
    CloseDrawer closeDrawer;
    SharedPreferences movie;
    String filename = "movie";
    ListView sublist;
    String[] sublistitems = new String[]{"Rated R", "Rated G"};
    ArrayAdapter<String> sublistArrayAdapter;
    NavigationListAdapter navigationListAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        closeDrawer = (CloseDrawer) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        return initializeEverything(v, inflater);
    }

    public View initializeEverything(View v, LayoutInflater inflater) {
        getListReady();
        sidelist = (ListView) v.findViewById(R.id.listviewsidemenu);


        List<String> wordList = new ArrayList<String>();
        Collections.addAll(wordList, sublistitems);


        navigationListAdapter = new NavigationListAdapter();
        sidelist.setAdapter(navigationListAdapter);
        movie = getActivity().getSharedPreferences(filename, 1);

        mPosition = movie.getInt("position", 0);
        if (mPosition != -1) {
            Log.e("mposition", mPosition + "");
//           sidelist.getChildAt(mPosition).setBackgroundColor(Color.GRAY);
        }

        sidelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                sidelist.setSelection(position);
                if (mPosition != -1) {
                    sidelist.getChildAt(mPosition).setBackgroundColor(Color.WHITE); //reset color of previously pressed item to white
                }
                sidelist.getChildAt(position).setBackgroundColor(Color.GRAY); //set current item to gray.
                mPosition = position; //put value of currently selected item to mPosition.

                SharedPreferences.Editor edit = movie.edit();
                edit.putInt("position", position);
                edit.commit();

                switch (position) {
                    case 0:
                        fragmentTransaction.replace(R.id.fragment_for_homepage, new MostPopularMovies());

                        break;
                    case 1:
                        fragmentTransaction.replace(R.id.fragment_for_homepage, new MostVotes());

                        break;
                    case 2:
                        fragmentTransaction.replace(R.id.fragment_for_homepage, new InTheaters());

                        break;
                    case 3:
                        fragmentTransaction.replace(R.id.fragment_for_homepage, new UpComingMovies());

                        break;


                }

                fragmentTransaction.commit();
                closeDrawer.closeDrawer();

            }
        });
        return v;
    }

    /**
     * @author manas shrestha
     * this method will just populate the itemlist
     */
    public void getListReady() {
        String[] items = new String[]{"Most Popular", "Most Votes", "In threaters", "Upcoming"};
        for (int i = 0; i < items.length; i++) {
            itemlist.add(items[i]);
        }
    }

    @Override
    public void closeDrawer() {
    }

    public class NavigationListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return itemlist.size();
        }

        @Override
        public Object getItem(int position) {
            return itemlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.simple_list_item, parent, false);
            ImageView imageView = (ImageView) v.findViewById(R.id.imageView1);
            TextView tv = (TextView) v.findViewById(R.id.tv1);
            tv.setText(itemlist.get(position));

            switch (position) {
                case 0:
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.mostpopular));
                    break;
                case 1:
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.mostvotes));
                    break;
                case 2:
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.intheaters));
                    break;
                case 3:
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.upcomingicon));
                    break;
            }
            return v;
        }
    }
}
