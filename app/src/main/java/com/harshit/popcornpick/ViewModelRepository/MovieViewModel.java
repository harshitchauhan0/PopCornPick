package com.harshit.popcornpick.ViewModelRepository;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.harshit.popcornpick.EntityAndDb.MovieModel;

import java.util.List;

public class MovieViewModel extends ViewModel {

    private final MovieRepository movieRepository;

    public MovieViewModel(){
        movieRepository = MovieRepository.getInstance();
    }
    public LiveData<List<MovieModel>> getPopularMoviesFromViewModel(){
        Log.v("TAG","This is from viewmodel");
        return movieRepository.getMoviesPopular();
    }

    public void searchPopularMovieFromViewModel(int page){
//        Log.v("TAG","This is from viewmodel"+page);
        movieRepository.searchMoviePopularFromRepository(page);
    }

    public void searchNextPage(){
        movieRepository.searchNextPageFromRepository();
    }

    public LiveData<List<MovieModel>> getTrendingMoviesFromViewModel(){
        Log.v("TAG","This is from viewmodel");
        return movieRepository.getTrendingPopular();
    }

    public void searchTrendingMovieFromViewModel(int page){
//        Log.v("TAG","This is from viewmodel"+page);
        movieRepository.searchMovieTrendingFromRepository(page);
    }

    public void searchTrendingNextPage(){
        movieRepository.searchTrendingNextPageFromRepository();
    }

    public void searchMoviefromViewModel(String query,int page){
        movieRepository.searchMovieFromRepository(query,page);
    }

    public LiveData<List<MovieModel>> getSearchedMovieFromViewModel(){
        return movieRepository.getSearchedMovies();
    }
}
