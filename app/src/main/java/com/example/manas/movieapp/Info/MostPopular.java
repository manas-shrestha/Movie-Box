package com.example.manas.movieapp.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manas on 3/28/2015.
 */
public class MostPopular {
    public String page;
    public List<DetailInfo> results = new ArrayList<>();

    public class DetailInfo {
        public String adult;
        public String backdrop_path;
        public String id;
        public String original_title;
        public String release_date;
        public String poster_path;
        public String popularity;
        public String title;
        public String video;
        public String vote_average;
        public String vote_count;

    }

}
