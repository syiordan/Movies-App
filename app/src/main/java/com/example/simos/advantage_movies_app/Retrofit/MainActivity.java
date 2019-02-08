 package com.example.simos.advantage_movies_app.Retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

 public class MainActivity {
     public static String BASE_URL =  "https://api.themoviedb.org/3/" ;
     public static Retrofit retrofit = null;


     public static Retrofit getClient() {
         if (retrofit == null) {







             retrofit = new Retrofit.Builder()
                     .baseUrl(BASE_URL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .build();
         }
         return retrofit;
     }

 }
