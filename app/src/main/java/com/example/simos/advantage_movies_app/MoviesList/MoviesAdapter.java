package com.example.simos.advantage_movies_app.MoviesList;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.simos.advantage_movies_app.Details.DetailsView;
import com.example.simos.advantage_movies_app.R;
import com.example.simos.advantage_movies_app.Retrofit.Movie_Object;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private MoviesView movieListActivity;
    private List<Movie_Object> movieList;
    private List<Movie_Object> originalMovieList;
    private ArrayList<Integer> movies_ids;
    private ArrayList<String> movies_titles,movies_dates,movies_images,movies_media;
    private String fromDate;
    private String toDate;
    SharedPreferences watchlist;
    SharedPreferences.Editor watchlist_editor;
    private  Boolean is_watchlist;

    public MoviesAdapter(MoviesView movieListActivity, List<Movie_Object> movieList, ArrayList<Integer> movies_ids, ArrayList<String> movies_titles, ArrayList<String> movies_dates, ArrayList<String> movies_images, ArrayList<String> movies_media,Boolean is_watchlist) {
        this.movieListActivity = movieListActivity;
        this.movieList = movieList;
        this.originalMovieList = movieList;
        this.movies_ids = movies_ids;
        this.movies_dates = movies_dates;
        this.movies_titles = movies_titles;
        this.movies_images = movies_images;
        this.movies_media = movies_media;
        this.is_watchlist = is_watchlist;
    }

    @NonNull

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);

        watchlist =parent.getContext().getSharedPreferences("WATCHLIST",0);
        watchlist_editor = watchlist.edit();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Movie_Object movie = movieList.get(position);

        if(movie.getOriginal_title()!=null){
            holder.tvMovieTitle.setText(movie.getOriginal_title());
        }
        else{
            holder.tvMovieTitle.setText(movie.getOriginal_name());
        }

        if(movie.getRelease_date()!=null){
            holder.tvReleaseDate.setText(movie.getRelease_date());
        }
        else{
            holder.tvReleaseDate.setText(movie.getFirst_air_date());
        }


        if(is_watchlist){
            holder.remove.setVisibility(View.VISIBLE);
        }
        else{
            holder.remove.setVisibility(View.INVISIBLE);
        }


    //    holder.tvMovieRatings.setText(String.valueOf(movie.getVote_average()));


        /*
        String img = "";
        if(this.movies_images.get(position).length()!=0){
            img = "https://image.tmdb.org/t/p/w200/" +this.movies_images.get(position);
        }
        else{
            img = "https://image.tmdb.org/t/p/w200/" + movie.getPoster_path();
        }
        */

        // loading album cover using Glide library
        String base = "https://image.tmdb.org/t/p/w200";
        String img =   movieList.get(position).getPoster_path();
        if(img == null){
            img = movieList.get(position).getPoster_path();
        }

        String im = base + img;
        Glide.with(movieListActivity)
                .load(im)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    //    holder.pbLoadImage.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                      //  holder.pbLoadImage.setVisibility(View.GONE);
                        return false;
                    }


                })
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))
                .into(holder.ivMovieThumb);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieListActivity.movie_on_click(holder.getAdapterPosition());
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieList.remove(holder.getAdapterPosition());
                movies_dates.remove(holder.getAdapterPosition());
                movies_ids.remove(holder.getAdapterPosition());
                movies_titles.remove(holder.getAdapterPosition());
                movies_images.remove(holder.getAdapterPosition());
                movies_media.remove(holder.getAdapterPosition());


                change_watchlist(movies_titles,"movies_titles");
                change_watchlist(movies_dates,"movies_dates");
                change_movies_ids(movies_ids,"movies_ids");
                change_watchlist(movies_images,"images");
                change_watchlist(movies_media,"media_types");


               notifyItemRemoved(holder.getAdapterPosition());
               notifyItemRangeChanged(holder.getAdapterPosition(),movieList.size());
            }
        });



    }


    public void change_watchlist(ArrayList<String> list,String key){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        watchlist_editor.putString(key, json);
        watchlist_editor.apply();
    }

    public void change_movies_ids(ArrayList<Integer> list,String key){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        watchlist_editor.putString(key, json);
        watchlist_editor.apply();
    }

    public void setMoviesIds(ArrayList<Integer> ids,ArrayList<String>movies_titles,ArrayList<String>movies_dates,ArrayList<String>movies_images,ArrayList<String>movies_media,Boolean is_watchlist){
        this.movies_ids = ids;
        this.movies_titles = movies_titles;
        this.movies_dates = movies_dates;
        this.movies_images = movies_images;
        this.movies_media = movies_media;
        this.is_watchlist = is_watchlist;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public void setFilterParameter(String fromDate, String toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMovieTitle;

        public TextView tvMovieRatings;

        public TextView tvReleaseDate;

        public ImageView ivMovieThumb;

        public TextView remove;

        public ProgressBar pbLoadImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvMovieTitle = itemView.findViewById(R.id.tv_movie_title);
            tvReleaseDate = itemView.findViewById(R.id.tv_release_date);
            ivMovieThumb = itemView.findViewById(R.id.iv_movie_thumb);
            remove = itemView.findViewById(R.id.remove);

        }
    }
}