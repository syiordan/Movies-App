package com.example.simos.advantage_movies_app.Details;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.view.ActionMode;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.simos.advantage_movies_app.R;
import com.example.simos.advantage_movies_app.Retrofit.Movie_Object;
import com.example.simos.advantage_movies_app.Retrofit.Videos_Object;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetailsView extends AppCompatActivity implements DetailsInterface.View,YouTubePlayer.OnInitializedListener  {
    private ImageView back;
    private TextView movieTitle;
    private TextView movie_release_date;
    private TextView ratings;
    private TextView overview;
    private TextView runtime;
    private TextView genre;
    private Button add_to_watch;
    private String media_type;
    private String image;
    private YouTubePlayerFragment video;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private TextView txtVideo;
    SharedPreferences watchlist;
    SharedPreferences.Editor watchlist_editor;
    ArrayList<Integer> ids;
    ArrayList<String> titles,dates,media_types,images;
    List<Videos_Object> videos;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private DetailsPresenter movieDetailsPresenter;
    public static final String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780/";
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.movie_details);

        watchlist = getApplicationContext().getSharedPreferences("WATCHLIST",0);
        watchlist_editor = watchlist.edit();
        ids=new ArrayList<>();
        titles = new ArrayList<>();
        dates = new ArrayList<>();
        media_types = new ArrayList<>();
        images = new ArrayList<>();


        titles =   getArrayList("movies_titles");
        ids =    getMoviesIds("movies_ids");
        dates =   getArrayList("movies_dates");
        media_types = getArrayList("media_types");
        images = getArrayList("images");


        if(titles==null){
            ids=new ArrayList<>();
            titles = new ArrayList<>();
            dates = new ArrayList<>();
            media_types = new ArrayList<>();
            images = new ArrayList<>();
        }


        findView();

        Intent mIntent = getIntent();
        final int movieId = mIntent.getIntExtra("movie_ids", 0);
        final String movie_title = mIntent.getStringExtra("movie_titles");
        final String movie_date = mIntent.getStringExtra("movie_dates");
        media_type = mIntent.getStringExtra("media_types");
        image = mIntent.getStringExtra("images");

        ids.add(movieId);
        titles.add(movie_title);
        dates.add(movie_date);
        media_types.add(media_type);
        images.add(image);



        movieDetailsPresenter = new DetailsPresenter(this);
        movieDetailsPresenter.requestMovieData(movieId,media_type);

        movieDetailsPresenter.requestVideos(movieId,media_type);

     
        add_to_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             view.startAnimation(buttonClick);
             add_watchlist(titles,"movies_titles");
             add_watchlist(dates,"movies_dates");
             add_movies_ids(ids,"movies_ids");
             add_watchlist(media_types,"media_types");
             add_watchlist(images,"images");
            }
        });


        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragTransaction.hide(video);
        fragTransaction.commit();

    }

    public ArrayList<String> getArrayList(String key){

        Gson gson = new Gson();
        String json = watchlist.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public ArrayList<Integer> getMoviesIds(String key){

        Gson gson = new Gson();
        String json = watchlist.getString(key, null);
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        return gson.fromJson(json, type);
    }


    public void add_watchlist(ArrayList<String> list,String key){
       Gson gson = new Gson();
       String json = gson.toJson(list);
       watchlist_editor.putString(key, json);
       watchlist_editor.apply();
   }

    public void add_movies_ids(ArrayList<Integer> list,String key){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        watchlist_editor.putString(key, json);
        watchlist_editor.apply();
    }

    private void findView() {

        back = findViewById(R.id.backdrop);
        movieTitle = findViewById(R.id.movie_title);
        genre = findViewById(R.id.genre);
        movie_release_date = findViewById(R.id.date);
        ratings = findViewById(R.id.ratings);
        overview = findViewById(R.id.overview);
        video = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        runtime = findViewById(R.id.runtime);
        add_to_watch = findViewById(R.id.add_to_watch);
        txtVideo = findViewById(R.id.tv_videos);


    }



    @Override
    public void setDataToViews(Movie_Object movie) {

        if (movie != null) {



            String genres ="";


            if(movie.getGenres().size() !=0){
                genres =  movie.getGenres().get(0).getName();
                genre.setText(genres);
            }


        

            if(media_type.equals("tv")){
                movieTitle.setText(movie.getOriginal_name());
                movie_release_date.setText(movie.getFirst_air_date());

            }
            else{
                movieTitle.setText(movie.getOriginal_title());
                movie_release_date.setText(movie.getRelease_date());
                runtime.setText(String.valueOf(movie.getRuntime()) + "min" );
            }


            ratings.setText(String.valueOf(movie.getVote_average()));
            overview.setText(movie.getOverview());


            // loading album cover using Glide library
            String img = BACKDROP_BASE_URL ;
            if(movie.getBackdrop_path()!=null){
                img = img + movie.getBackdrop_path();
            }
            else{
                img = img + movie.getPoster_path();
            }
            Glide.with(this)
                    .load(img)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//               
                            return false;
                        }
                    })
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))
                    .into(back);


        }

    }


    @Override
    public void setVideoToViews(Videos_Object vid) {

        if (vid != null) {


            key = vid.getKey();
            String site = vid.getSite();
            String type = vid.getType();

            txtVideo.setVisibility(View.VISIBLE);

            FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
            fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                    android.R.animator.fade_out);
            fragTransaction.show(video);
            fragTransaction.commit();

            video.initialize("AIzaSyCPmARboNNcszBZKyZk6qcE_GOJeS0YtPg", this);


        }

    }


    @Override
    public void onResponseFailure(Throwable throwable) {

        Snackbar.make(findViewById(R.id.main_content), "ERROR", Snackbar.LENGTH_LONG).show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieDetailsPresenter.onDestroy();
    }


    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
      //  if (!wasRestored) {
            youTubePlayer.cueVideo(key);
      //  }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        } else {
            String errorMessage = String.format("There was an error initializing the YouTubePlayer (%1$s)", youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    public void hideVideo(){
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragTransaction.hide(video);
        fragTransaction.commit();
    }




}