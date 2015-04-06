package com.example.manas.movieapp;

import com.example.manas.movieapp.Info.ActorInfo;
import com.example.manas.movieapp.Info.MostPopular;
import com.example.manas.movieapp.Info.SinlgeMovie;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Manas on 3/28/2015.
 */
public interface API {
    @GET("/discover/movie?sort_by=popularity.desc&api_key=602db3c7d00ed71f4002c877f7f89cab")
    void getPopular(@Query("page") String page, retrofit.Callback<MostPopular> callback);


    @GET("/movie/{id}?api_key=602db3c7d00ed71f4002c877f7f89cab&append_to_response=releases,trailers,reviews,casts")
    void getSingle(@Path("id") String id, retrofit.Callback<SinlgeMovie> callback);

    @GET("/discover/movie?sort_by=vote_average.desc&api_key=602db3c7d00ed71f4002c877f7f89cab")
    void getMostVotes(@Query("page") String page, retrofit.Callback<MostPopular> callback);

    @GET("/discover/movie?primary_release_date.gte=2015-03-29&primary_release_date.lte=2015-03-30&api_key=602db3c7d00ed71f4002c877f7f89cab")
    void getInTheaters(retrofit.Callback<MostPopular> callback);


    @GET("/discover/movie?with_genres=18&primary_release_year=2015&api_key=602db3c7d00ed71f4002c877f7f89cab")
    void getUpComing(retrofit.Callback<MostPopular> callback);


    @GET("/search/movie?api_key=602db3c7d00ed71f4002c877f7f89cab&")
    void search(@Query("query") String query, retrofit.Callback<MostPopular> callback);

    @GET("/person/{id}&?api_key=602db3c7d00ed71f4002c877f7f89cab")
    void getProfile(@Path("id") String id, retrofit.Callback<ActorInfo> callback);
}