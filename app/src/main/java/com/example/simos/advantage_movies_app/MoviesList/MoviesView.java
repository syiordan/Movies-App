package com.example.simos.advantage_movies_app.MoviesList;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.simos.advantage_movies_app.Details.DetailsView;
import com.example.simos.advantage_movies_app.R;
import com.example.simos.advantage_movies_app.Retrofit.Movie_Object;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MoviesView extends AppCompatActivity implements MovieListInterface.View {
    
    private MoviesPresenter movies_presenter;
    private RecyclerView movies_recycler;
    private List<Movie_Object> moviesList;
    private MoviesAdapter moviesAdapter;
    Movie_Object temp_movie;
    private int pageNo = 1;
    private GridLayoutManager layout_manager;
    SharedPreferences watchlist;
    SharedPreferences.Editor watchlist_editor;
    ArrayList<String> movies_titles,movies_dates,movies_images,movies_media_types;
    ArrayList<Integer> movies_ids;
    Button watch;
    SearchView searchView;
    boolean is_watchlist=false;
    SearchManager searchManager;
    TextView noMovies;
    Gson gson;
    String movies_lst;
    FloatingActionButton fab;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.movies_list_view);

        //these will keep ids,dates etc with SharedPreferences for watchlist
        movies_dates = new ArrayList<>();
        movies_ids = new ArrayList<>();
        movies_titles = new ArrayList<>();
        movies_images = new ArrayList<>();
        movies_media_types= new ArrayList<>();
        movies_recycler = (RecyclerView)findViewById(R.id.rv_movie_list);
        moviesList = new ArrayList<>();


        fab = (FloatingActionButton)findViewById(R.id.fab);  //watchlist button
        noMovies = (TextView)findViewById(R.id.tv_empty_view);

        //for change orientation problem get data
        if(savedInstanceState!=null){
            String mv = savedInstanceState.getString("mvList");
            gson  = new Gson();
            moviesList = gson.fromJson(mv,new TypeToken<List<Movie_Object>>(){}.getType());
            movies_titles = savedInstanceState.getStringArrayList("movies_titles");
            movies_ids=savedInstanceState.getIntegerArrayList("movies_ids");
            movies_dates = savedInstanceState.getStringArrayList("movies_dates");
            movies_images = savedInstanceState.getStringArrayList("images");
            movies_media_types= savedInstanceState.getStringArrayList("save_media");
            pageNo = savedInstanceState.getInt("page");
            is_watchlist = savedInstanceState.getBoolean("is_watch");

        }

        moviesAdapter = new MoviesAdapter(this, moviesList,movies_ids,movies_titles,movies_dates,movies_images,movies_media_types,is_watchlist);
        watchlist = getApplicationContext().getSharedPreferences("WATCHLIST",0);
        watchlist_editor=watchlist.edit();

        layout_manager = new GridLayoutManager(this,1);
        movies_recycler.setLayoutManager(layout_manager);
        movies_recycler.setAdapter(moviesAdapter);



        if(moviesList.size()!=0){
            noMovies.setVisibility(View.INVISIBLE);
        }
        else{
            noMovies.setVisibility(View.VISIBLE);
        }

        if(movies_ids!=null){
            if(movies_ids.size()!=0){
               fab.setVisibility(View.VISIBLE);

            }
            else{
                fab.setVisibility(View.INVISIBLE);

            }
        }
        else{
            fab.setVisibility(View.INVISIBLE);

        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_watchlist = true;
                moviesList.clear();
                moviesAdapter.notifyDataSetChanged();

                movies_titles =   getArrayList("movies_titles");
                movies_ids =    getMoviesIds("movies_ids");
                movies_dates =   getArrayList("movies_dates");
                movies_images = getArrayList("images");
                movies_media_types = getArrayList("media_types");
                searchView.setQuery("",true);


                for(int i=0;i<movies_ids.size();i++){
                    temp_movie = new Movie_Object();
                    temp_movie.setId(movies_ids.get(i));
                    temp_movie.setRelease_date(movies_dates.get(i));
                    temp_movie.setOriginal_title(movies_titles.get(i));
                    temp_movie.setPoster_path(movies_images.get(i));
                    temp_movie.setMedia_type(movies_media_types.get(i));
                    moviesList.add(temp_movie);
                }
                moviesAdapter.setMoviesIds(movies_ids,movies_titles,movies_dates,movies_images,movies_media_types,is_watchlist);
                moviesAdapter.notifyDataSetChanged();
            }
        });


        list_scroll();
        movies_presenter = new MoviesPresenter(this);

    }




    protected void onSaveInstanceState(Bundle icicle) {
        gson = new Gson();
        super.onSaveInstanceState(icicle);
        icicle.putStringArrayList("movies_titles",movies_titles);
        icicle.putIntegerArrayList("movies_ids",movies_ids);
        icicle.putStringArrayList("movies_dates",movies_dates);
        icicle.putStringArrayList("images",movies_images);
        icicle.putBoolean("is_watch",is_watchlist);
        icicle.putInt("page",pageNo);
        movies_lst= gson.toJson(moviesList);
        icicle.putString("mvList",movies_lst);

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu, menu);


        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                moviesList.clear();
                is_watchlist = false;
                moviesAdapter.setMoviesIds(movies_ids,movies_titles,movies_dates,movies_images,movies_media_types,is_watchlist);
                moviesAdapter.notifyDataSetChanged();
                noMovies.setVisibility(View.INVISIBLE);


                movies_presenter.requestDataFromServer(searchView.getQuery().toString());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(getArrayList("movies_ids")!=null){
            movies_titles = getArrayList("movies_titles");
            movies_ids = getMoviesIds("movies_ids");
            movies_dates = getArrayList("movies_dates");
            movies_images = getArrayList("images");
            movies_media_types = getArrayList("media_types");
        }

        if(movies_ids!=null){
            if(movies_ids.size()!=0){
                fab.setVisibility(View.VISIBLE);

            }
            else{
                fab.setVisibility(View.INVISIBLE);

            }
        }
        else{
            fab.setVisibility(View.INVISIBLE);

        }

        if(moviesList.size()!=0){
            noMovies.setVisibility(View.INVISIBLE);

        }
        else{
            noMovies.setVisibility(View.VISIBLE);
        }

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


    private void list_scroll() {

        movies_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (movies_recycler.canScrollVertically(1)) {

                    movies_presenter.getMoreData(pageNo,searchView.getQuery().toString());
                }
            }

        });


    }

    public void movie_on_click(int position) {
        is_watchlist = false;

        if (position == -1) {
            return;
        }
        Intent detailIntent = new Intent(this,DetailsView.class);
        detailIntent.putExtra("movie_ids", moviesList.get(position).getId());

        if(moviesList.get(position).getMedia_type().equals("tv")){
            detailIntent.putExtra("movie_dates", moviesList.get(position).getFirst_air_date());
            detailIntent.putExtra("images",moviesList.get(position).getPoster_path());
            detailIntent.putExtra("media_types",moviesList.get(position).getMedia_type());
            detailIntent.putExtra("movie_titles", moviesList.get(position).getOriginal_name());
        }
        else{
            detailIntent.putExtra("movie_dates", moviesList.get(position).getRelease_date());
            detailIntent.putExtra("images",moviesList.get(position).getPoster_path());
            detailIntent.putExtra("media_types",moviesList.get(position).getMedia_type());
            detailIntent.putExtra("movie_titles", moviesList.get(position).getOriginal_title());
        }


        startActivity(detailIntent);
    }


    @Override
    public void update_list_data(List<Movie_Object> movieArrayList) {
        moviesList.addAll(movieArrayList);
        moviesAdapter.notifyDataSetChanged();

        if(moviesList.size()==0){
            noMovies.setVisibility(View.VISIBLE);
        }
        pageNo++;
    }


}





