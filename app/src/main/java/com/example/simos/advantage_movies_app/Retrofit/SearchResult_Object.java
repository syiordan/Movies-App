package com.example.simos.advantage_movies_app.Retrofit;

import com.example.simos.advantage_movies_app.Retrofit.Movie_Object;

import java.util.List;

public class SearchResult_Object {

    private int page;
    private int total_result;
    private int total_pages;
    private List<Movie_Object> results;

    public void setPage(int page) {
        this.page = page;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public void setTotal_result(int total_result) {
        this.total_result = total_result;
    }

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_result() {
        return total_result;
    }

    public void setResults(List<Movie_Object> results) {
        this.results = results;
    }

    public List<Movie_Object> getResults() {
        return results;
    }
}
