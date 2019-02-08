package com.example.simos.advantage_movies_app.MoviesList;


import com.example.simos.advantage_movies_app.Retrofit.Movie_Object;

import java.util.List;

public class MoviesPresenter  implements MovieListInterface.Presenter, MovieListInterface.Model.OnFinishedListener {

    private MovieListInterface.View movieListView;

    private MovieListInterface.Model movieListModel;

    public MoviesPresenter(MovieListInterface.View movieListView) {
        this.movieListView = movieListView;
        movieListModel = new MoviesModel();
    }

    @Override
    public void onDestroy() {
        this.movieListView = null;
    }

    @Override
    public void getMoreData(int pageNo,String query) {


        movieListModel.getMovieList(this,query, pageNo);
    }

    @Override
    public void requestDataFromServer(String query) {


        movieListModel.getMovieList(this,query ,1);
    }

    @Override
    public void onFinished(List<Movie_Object> movieArrayList) {
        movieListView.update_list_data(movieArrayList);

    }

    @Override
    public void onFailure(Throwable t) {


    }


}