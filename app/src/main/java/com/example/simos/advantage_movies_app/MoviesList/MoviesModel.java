package com.example.simos.advantage_movies_app.MoviesList;

import android.util.Log;

import com.example.simos.advantage_movies_app.Retrofit.MainActivity;
import com.example.simos.advantage_movies_app.Retrofit.Movie_Object;
import com.example.simos.advantage_movies_app.Retrofit.Movies_endpoints;
import com.example.simos.advantage_movies_app.Retrofit.SearchResult_Object;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesModel  implements MovieListInterface.Model {

    @Override
    public void getMovieList(final OnFinishedListener onFinishedListener, String movie , int pageNo) {

        Movies_endpoints apiService =
                MainActivity.getClient().create(Movies_endpoints.class);


        Call<SearchResult_Object> call = apiService.getMovies( "6b2e856adafcc7be98bdf0d8b076851c" ,movie,String.valueOf(pageNo));


        call.enqueue(new Callback<SearchResult_Object>() {

            @Override
            public void onResponse(Call<SearchResult_Object> call, Response<SearchResult_Object> response) {



                if(response.body()!=null){
                    List<Movie_Object> movies = response.body().getResults();
                    Log.i("NUM", "Number of movies received: " + movies.size());
                    onFinishedListener.onFinished(movies);
                }

            }

            @Override
            public void onFailure(Call<SearchResult_Object> call, Throwable t) {
                // Log error here since request failed

                 onFinishedListener.onFailure(t);
            }


        });
    }


}
