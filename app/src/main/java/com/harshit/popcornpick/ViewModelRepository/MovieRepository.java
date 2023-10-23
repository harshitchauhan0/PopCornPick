package com.harshit.popcornpick.ViewModelRepository;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.harshit.popcornpick.EntityAndDb.MovieModel;
import com.harshit.popcornpick.Helper.MovieClient;
import java.util.List;
public class MovieRepository {
    private static MovieRepository instance;
    private MovieClient movieClient;
    private int page,page1;
    public static MovieRepository getInstance(){
        if(instance == null){
            instance  = new MovieRepository();
        }
        return instance;
    }
    private MovieRepository(){
        movieClient = MovieClient.getInstance();
    }
    public LiveData<List<MovieModel>> getMoviesPopular(){
//        Log.v("TAG","This is from repository"+page);
        return movieClient.getMoviesPopular();
    }

    public void searchMoviePopularFromRepository(int page){
//        Log.v("TAG","This is from repository"+page);
        this.page = page;
        movieClient.searchPopularMovies(page);
    }

    public void searchMovieFromRepository(String query,int page){
        movieClient.searchMovieApi(query,page);
    }

    public LiveData<List<MovieModel>> getSearchedMovies(){
        Log.v("TAG","This is from repository"+page);
        return movieClient.getMovieSearchList();
    }


    public void searchNextPageFromRepository(){
        movieClient.searchPopularMovies(++page);
    }

    public LiveData<List<MovieModel>> getTrendingPopular(){
//        Log.v("TAG","This is from repository"+page);
        return movieClient.getMoviesTrendingList();
    }

    public void searchMovieTrendingFromRepository(int page){
//        Log.v("TAG","This is from repository"+page);
        this.page1 = page;
        movieClient.searchTrendingMovies(page);
    }

    public void searchTrendingNextPageFromRepository(){
        movieClient.searchTrendingMovies(++page1);
    }
}
