package com.example.manas.movieapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manas.movieapp.ActorProfile;
import com.example.manas.movieapp.Info.SinlgeMovie;
import com.example.manas.movieapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Manas on 3/29/2015.
 */
public class CastAdapter extends BaseAdapter {
    SinlgeMovie sinlgeMovie;
    Context context;
    LayoutInflater inflater;

    public CastAdapter(LayoutInflater inflater, Context C, SinlgeMovie sinlgeMovie) {
        this.sinlgeMovie = sinlgeMovie;
        this.context = C;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return sinlgeMovie.casts.cast.size();
    }

    @Override
    public Object getItem(int position) {
        return sinlgeMovie.casts.cast.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View v = inflater.inflate(R.layout.items_for_castlistview, parent, false);
        ImageView castiv = (ImageView) v.findViewById(R.id.castIV);
        TextView casttv = (TextView) v.findViewById(R.id.castTV);
        casttv.setText(sinlgeMovie.casts.cast.get(position).name + "\nAs \n" + sinlgeMovie.casts.cast.get(position).character);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500" + sinlgeMovie.casts.cast.get(position).profile_path)
                .error(R.drawable.actorpicdefault).placeholder(R.drawable.actorpicdefault).into(castiv);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", sinlgeMovie.casts.cast.get(position).id);
                Intent i = new Intent(context, ActorProfile.class);
                i.putExtras(bundle);
                context.startActivity(i);

            }
        });
        return v;
    }
}


