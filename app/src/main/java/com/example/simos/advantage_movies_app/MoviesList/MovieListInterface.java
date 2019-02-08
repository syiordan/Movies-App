package com.example.simos.advantage_movies_app.MoviesList;


import android.content.Context;

import com.example.simos.advantage_movies_app.Retrofit.Movie_Object;

import java.util.ArrayList;
import java.util.List;

public interface MovieListInterface {

        interface Model {

            interface OnFinishedListener {
                void onFinished(List<Movie_Object> movieArrayList);

                void onFailure(Throwable t);
            }

            void getMovieList(OnFinishedListener onFinishedListener,String movie, int pageNo);

        }

        interface View {

            void update_list_data(List<Movie_Object> movieArrayList);

        }

        interface Presenter {

            void onDestroy();

            void getMoreData(int pageNo,String query);

            void requestDataFromServer(String query);


        }
    }


