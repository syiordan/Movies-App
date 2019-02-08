package com.example.simos.advantage_movies_app.Retrofit;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie_Object {
    private String original_name;
    private int id;
    private double vote_average;
    private String poster_path;
    private String backdrop_path;
    private String first_air_date;
    private String original_title;
    private String release_date;
    private String overview;
    private int runtime;
    private String media_type;
    private ArrayList<Genres> genres;

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public ArrayList<Genres> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genres> genres) {
        this.genres = genres;
    }

    /*
    private JSONArray genres;


    public JSONArray getGenres() {
        return genres;
    }

    public void setGenres(JSONArray genres) {
        this.genres = genres;
    }
    */

    public double getVote_average() {
        return vote_average;
    }

    public int getId() {
        return id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }
}
