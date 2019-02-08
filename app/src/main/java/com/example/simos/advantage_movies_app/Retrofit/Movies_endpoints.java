package com.example.simos.advantage_movies_app.Retrofit;

import com.example.simos.advantage_movies_app.Retrofit.Movie_Object;
import com.example.simos.advantage_movies_app.Retrofit.SearchResult_Object;
import com.google.gson.JsonElement;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Movies_endpoints {

    @GET("search/multi")
    Call<SearchResult_Object> getMovies(@Query("api_key") String apiKey, @Query("query") String query, @Query ("page") String page_num);

    @GET("movie/{movie_id}")
    Call<Movie_Object> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("tv/{movie_id}")
    Call<Movie_Object> getTvDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey);


    @GET("movie/{movie_id}/videos")
    Call<SearchVideos_Object> getVideos(@Path("movie_id") int movieId, @Query("api_key") String apiKey);




}
