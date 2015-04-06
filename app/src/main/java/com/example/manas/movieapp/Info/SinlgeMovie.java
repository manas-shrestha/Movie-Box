package com.example.manas.movieapp.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manas on 3/28/2015.
 */
public class SinlgeMovie {
    public String adult;
    public String backdrop_path;

    public String budget;
    public List<genres> genres = new ArrayList<>();
    public String homepage;
    public String id;
    public String imdb_id;
    public String original_language;
    public String original_title;
    public String overview;
    public String popularity;
    public String poster_path;
    public String revenue;
    public List<production_companies> production_companies = new ArrayList<>();
    public List<production_countries> production_countries = new ArrayList<>();
    public String status;
    public String tagline;
    public String title;
    public String video;
    public String vote_average;
    public String vote_count;
    public Reviews reviews;
    public Casts casts;

    public class Casts {
        public List<Cast> cast;
    }

    public class Cast {
        public String cast_id;
        public String character;
        public String credit_id;
        public String id;
        public String name;
        public String order;
        public String profile_path;
    }

    public class Reviews {
        public String pages;
        public List<Results> results;

    }

    public class Results {
        public String id;
        public String author;
        public String content;
        public String url;
    }


    public class production_countries {
        public String iso_3166_1;
        public String name;
    }

    public class production_companies {
        public String name;
        public String id;
    }

    public class genres {
        public String id;
        public String name;
    }

}
