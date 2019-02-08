package com.example.simos.advantage_movies_app.Details;

import com.example.simos.advantage_movies_app.Retrofit.Movie_Object;
import com.example.simos.advantage_movies_app.Retrofit.Videos_Object;

import java.util.List;

public interface DetailsInterface {

    interface Model {

        interface OnFinishedListener {
            void onFinished(Movie_Object movie);
            void onFinishedVideo(List<Videos_Object> videos);
            void onFinishedVideoError();
            void onFailure(Throwable t);
        }

        void getMovieDetails(OnFinishedListener onFinishedListener, int movieId);
        void geTvDetails(OnFinishedListener onFinishedListener, int movieId);
        void getVideos(OnFinishedListener onFinishedListener, int movieId);
    }

    interface View {


        void setDataToViews(Movie_Object movie);
        void setVideoToViews(Videos_Object vid);
        void hideVideo();

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestMovieData(int movieId,String media_type);
       void requestVideos(int movieId,String media_type);
    }
}