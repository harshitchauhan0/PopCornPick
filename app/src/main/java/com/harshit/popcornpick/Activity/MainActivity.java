package com.harshit.popcornpick.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.harshit.popcornpick.Adapter.MyRecyclerViewAdapter;
import com.harshit.popcornpick.Adapter.OnMovieListener;
import com.harshit.popcornpick.EntityAndDb.MovieModel;
import com.harshit.popcornpick.EntityAndDb.MovieModelList;
import com.harshit.popcornpick.R;
import com.harshit.popcornpick.ViewModelRepository.MovieViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMovieListener{

    private RecyclerView recyclerViewNewMovies,recyclerViewPopularMovies;
    private ProgressBar loading1,loading2;
    private MovieViewModel movieViewModel;

    private ImageView favouriteBTN,logoutBTN;
    private MyRecyclerViewAdapter recyclerPopularViewAdapter,recyclerNewViewAdapter;
    private static final boolean POPULAR_ADAPTER = true;
    private static final boolean Trending_ADAPTER = false;

    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.searchPopularMovieFromViewModel(1);
        movieViewModel.searchTrendingMovieFromViewModel(1);
        GetData();
    }

    private void initView(){
        logoutBTN = findViewById(R.id.imageView6);
        logoutBTN.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        });
        recyclerViewNewMovies = findViewById(R.id.view1);
        recyclerViewNewMovies.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewPopularMovies = findViewById(R.id.view2);
        recyclerViewPopularMovies.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        loading1 = findViewById(R.id.loading1);
        loading2 = findViewById(R.id.loading2);
        loading1.setVisibility(View.VISIBLE);
        loading2.setVisibility(View.VISIBLE);
        recyclerPopularViewAdapter = new MyRecyclerViewAdapter(this,POPULAR_ADAPTER);
        recyclerNewViewAdapter = new MyRecyclerViewAdapter(this,Trending_ADAPTER);
        recyclerViewPopularMovies.setAdapter(recyclerPopularViewAdapter);
        loading1.setVisibility(View.GONE);
        loading2.setVisibility(View.GONE);
        recyclerViewPopularMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    movieViewModel.searchNextPage();
                }
            }
        });

        recyclerViewNewMovies.setAdapter(recyclerNewViewAdapter);
        recyclerViewNewMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                movieViewModel.searchTrendingNextPage();
            }
        });

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieViewModel.searchMoviefromViewModel(query,1);
                Log.v("REAL_TAG",query);
                movieViewModel.getSearchedMovieFromViewModel().observe(MainActivity.this, new Observer<List<MovieModel>>() {
                    @Override
                    public void onChanged(List<MovieModel> list) {
                        if(list==null){
                            Log.v("REAL_TAG","this list is null");
                        }
                        else{
                            MovieModelList movieModelList = new MovieModelList();
                            movieModelList.setList(list);
                            Intent i = new Intent(MainActivity.this, SearchActivity.class);
                            i.putExtra("movielist", movieModelList);
                            startActivity(i);
                        }
                    }
                });

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        favouriteBTN = findViewById(R.id.imageView3);
        favouriteBTN.setOnClickListener(v->{
            Intent i = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(i);
        });
    }

    private void GetData(){
        if(movieViewModel!=null){
            movieViewModel.getPopularMoviesFromViewModel().observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(List<MovieModel> movieModels) {
                    if (movieModels != null) {
                        recyclerPopularViewAdapter.setList(movieModels);
                    }
                }
            });
            movieViewModel.getTrendingMoviesFromViewModel().observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(List<MovieModel> movieModels) {
                    if(movieViewModel!=null){
                        recyclerNewViewAdapter.setList(movieModels);
                        Log.v("TAG","done");
                    }
                }
            });
        }
    }
    @Override
    public void onMovieClick(int position,boolean adapter) {
            Intent i =new Intent(this, DetailActivity.class);
            MovieModel model;
            MovieModel popular = recyclerPopularViewAdapter.getClickedMovie(position);
            MovieModel trending = recyclerNewViewAdapter.getClickedMovie(position);
            if(adapter == POPULAR_ADAPTER){
                model = popular;
            }
            else{
                model = trending;
            }

            if(model!=null){
                int id = model.getId();
                i.putExtra("movie",id);
                Log.v("TAG","The model is succes");
                startActivity(i);

            }
            else{
                Log.v("TAG","There is problem in intent");
            }

    }
}