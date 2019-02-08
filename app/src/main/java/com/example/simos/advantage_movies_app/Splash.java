package com.example.simos.advantage_movies_app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.simos.advantage_movies_app.MoviesList.MoviesView;

public class Splash  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MoviesView.class);
        startActivity(intent);
        finish();
    }
}
