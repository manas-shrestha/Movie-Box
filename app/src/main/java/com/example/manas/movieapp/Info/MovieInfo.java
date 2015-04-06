package com.example.manas.movieapp.Info;

/**
 * Created by Manas on 3/23/2015.
 */
public class MovieInfo {
    public String Name;
    public int poster_id;
    public String rating;
    public String synopsis;

    public MovieInfo(String Name, int poster_id, String rating, String synopsis) {
        this.Name = Name;
        this.poster_id = poster_id;
        this.rating = rating;
        this.synopsis = synopsis;
    }

}
