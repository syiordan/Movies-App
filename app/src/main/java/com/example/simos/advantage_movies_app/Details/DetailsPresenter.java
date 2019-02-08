package com.example.simos.advantage_movies_app.Details;


import android.util.Log;

import com.example.simos.advantage_movies_app.Retrofit.Movie_Object;
import com.example.simos.advantage_movies_app.Retrofit.Videos_Object;

import java.util.List;

public class DetailsPresenter implements DetailsInterface.Presenter, DetailsInterface.Model.OnFinishedListener {

    private DetailsInterface.View movieView;
    private DetailsInterface.Model movieModel;

    public DetailsPresenter(DetailsInterface.View movieView) {
        this.movieView = movieView;
        this.movieModel = new DetailsModel();
    }

    @Override
    public void onDestroy() {

        movieView = null;
    }

    @Override
    public void requestMovieData(int movieId,String media_type) {

        
        if(media_type.equals("tv")){
            movieModel.geTvDetails(this,movieId);
        }
        else{
            movieModel.getMovieDetails(this, movieId);
        }


    }

    @Override
    public void requestVideos(int movieId, String media_type) {

      
        if(media_type.equals("tv")){
        }
        else{
        movieModel.getVideos(this, movieId);

        }


    }


    @Override
    public void onFinished(Movie_Object movie) {

      
        movieView.setDataToViews(movie);
    }

    @Override
    public void onFailure(Throwable t) {
       
        movieView.onResponseFailure(t);
    }


    @Override
    public void onFinishedVideo(List<Videos_Object> movie) {
       
        movieView.setVideoToViews(movie.get(0));
    }



    @Override
    public void onFinishedVideoError() {

        Log.i("VIDEO3","ok") ;
        movieView.hideVideo();
    }

}