package com.example.manas.movieapp.Adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.manas.movieapp.Info.MostPopular;
import com.example.manas.movieapp.MainActivity;
import com.example.manas.movieapp.utils.DatabaseHelper;
import com.example.manas.movieapp.R;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Manas on 3/23/2015.
 */
public class HomeFragmentRCVAdapter extends RecyclerView.Adapter<HomeFragmentRCVAdapter.ViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    MainActivity mainActivity;
    MostPopular mostPopular = new MostPopular();

    public HomeFragmentRCVAdapter(Context c) {
        this.context = c;
        layoutInflater = layoutInflater.from(context);

        this.mainActivity = (MainActivity) c;
    }

    public void setMostPopularObject(MostPopular mostPopular) {
        this.mostPopular = mostPopular;
        notifyDataSetChanged();
    }

    public void addMostPopularObject(MostPopular mostPopular) {
        this.mostPopular = mostPopular;

    }

    @Override
    public HomeFragmentRCVAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = layoutInflater.inflate(R.layout.card_view_for_recycler_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeFragmentRCVAdapter.ViewHolder holder, int i) {
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500" + mostPopular.results.get(i).backdrop_path).error(R.drawable.picturenotavailable).into(holder.moviePoster, new Callback() {
            @Override
            public void onSuccess() {
                holder.wheel.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {

            }
        });
        holder.movieName.setText(mostPopular.results.get(i).title);
        holder.id = mostPopular.results.get(i).id;
        holder.movieRating.setRating(Float.parseFloat(mostPopular.results.get(i).vote_average) / 2);
        DatabaseHelper db = new DatabaseHelper(context);
        db.getWritableDatabase();
    }

    @Override
    public int getItemCount() {
        return mostPopular.results.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView moviePoster;
        TextView movieName;
        String id;
        RatingBar movieRating;
        ViewGroup viewGroup;
        ImageView bookmark;
        RelativeLayout moviePosterBottom;
        ProgressWheel wheel;

        public ViewHolder(View itemView) {
            super(itemView);
            wheel = (ProgressWheel) itemView.findViewById(R.id.progress_wheel);
            viewGroup = (ViewGroup) itemView.findViewById(R.id.container_a);
            bookmark = (ImageView) itemView.findViewById(R.id.bookmarkIV);
            moviePoster = (ImageView) itemView.findViewById(R.id.moviePoster);
            movieName = (TextView) itemView.findViewById(R.id.movieName);
            movieRating = (RatingBar) itemView.findViewById(R.id.movieRating);

            LayerDrawable stars = (LayerDrawable) movieRating.getProgressDrawable();
            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            moviePosterBottom = (RelativeLayout) itemView.findViewById(R.id.moviePosterBottom);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            Bundle bundle = new Bundle();
            bundle.putString("id", id);

            mainActivity.startSingleMovieViewActivity(bundle, viewGroup);

        }


    }


}
