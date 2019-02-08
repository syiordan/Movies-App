package com.example.simos.advantage_movies_app.Retrofit;

import java.util.List;

/**
 * Created by iqtaxi on 07/02/2019.
 */

public class SearchVideos_Object {

    private List<Videos_Object> results;
    private int id;


    public SearchVideos_Object(int id,List<Videos_Object> list){
        this.id = id;
        this.results = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Videos_Object> getResults() {
        return results;
    }

    public int getResultsSize() {
        return results.size();
    }

    public void setResults(List<Videos_Object> results) {
        this.results = results;
    }


}
