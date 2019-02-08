package com.example.simos.advantage_movies_app.Details;


import android.util.Log;

import com.example.simos.advantage_movies_app.Retrofit.MainActivity;
import com.example.simos.advantage_movies_app.Retrofit.Movie_Object;
import com.example.simos.advantage_movies_app.Retrofit.Movies_endpoints;
import com.example.simos.advantage_movies_app.Retrofit.SearchVideos_Object;
import com.example.simos.advantage_movies_app.Retrofit.Videos_Object;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsModel implements DetailsInterface.Model {


    @Override
    public void getMovieDetails(final OnFinishedListener onFinishedListener, int movieId) {

        Movies_endpoints apiService =
                MainActivity.getClient().create(Movies_endpoints.class);

        Call<Movie_Object> call = apiService.getMovieDetails(movieId, "6b2e856adafcc7be98bdf0d8b076851c");
        call.enqueue(new Callback<Movie_Object>() {
            @Override
            public void onResponse(Call<Movie_Object> call, Response<Movie_Object> response) {
                Movie_Object movie = response.body();

                onFinishedListener.onFinished(movie);
            }

            @Override
            public void onFailure(Call<Movie_Object> call, Throwable t) {

                onFinishedListener.onFailure(t);
            }
        });

    }

    @Override
    public void geTvDetails(final OnFinishedListener onFinishedListener, int movieId) {

        Movies_endpoints apiService =
                MainActivity.getClient().create(Movies_endpoints.class);

        Call<Movie_Object> call = apiService.getTvDetails(movieId, "6b2e856adafcc7be98bdf0d8b076851c");
        call.enqueue(new Callback<Movie_Object>() {
            @Override
            public void onResponse(Call<Movie_Object> call, Response<Movie_Object> response) {
                Movie_Object movie = response.body();

                onFinishedListener.onFinished(movie);
            }

            @Override
            public void onFailure(Call<Movie_Object> call, Throwable t) {

                onFinishedListener.onFailure(t);
            }
        });
    }





    @Override
    public void getVideos(final OnFinishedListener onFinishedListener, int movieId) {

        Movies_endpoints apiService =
                MainActivity.getClient().create(Movies_endpoints.class);

        Call<SearchVideos_Object> call = apiService.getVideos(movieId, "6b2e856adafcc7be98bdf0d8b076851c");

        Log.i("VIDEO",String.valueOf(call.request().url()) );


        call.enqueue(new Callback< SearchVideos_Object>() {


            @Override
            public void onResponse(Call< SearchVideos_Object> call, Response< SearchVideos_Object> response) {

                if(response.body().getResultsSize()==0){
                    onFinishedListener.onFinishedVideoError();
                }
                else{
                    Log.i("VIDEO2",String.valueOf(response.body()) );
                    if(response.body().getResults()!=null){
                        List<Videos_Object> videos = response.body().getResults();

                        if(videos!=null){
                            onFinishedListener.onFinishedVideo(videos);
                        }

                    }
                }




            }

            @Override
            public void onFailure(Call<SearchVideos_Object> call, Throwable t) {

                Log.i("VIDEO2",String.valueOf(t) );
                onFinishedListener.onFailure(t);
            }
        });
    }


}