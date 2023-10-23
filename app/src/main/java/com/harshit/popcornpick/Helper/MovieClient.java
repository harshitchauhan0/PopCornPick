package com.harshit.popcornpick.Helper;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.harshit.popcornpick.EntityAndDb.Crediantials;
import com.harshit.popcornpick.EntityAndDb.MovieModel;
import com.harshit.popcornpick.EntityAndDb.MovieResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieClient{

    private MutableLiveData<List<MovieModel>> moviesPopularList;
    private MutableLiveData<List<MovieModel>> moviesTrendingList;

    private MutableLiveData<List<MovieModel>> movieSearchList;
    private RetrofitMoviesRunnable retrofitMoviesRunnable;
    private static MovieClient instance;
    private RetrofitMoviesNewRunnable retrofitMoviesNewRunnable;

    private RetrieveMoviesRunnableSearch retrieveMoviesRunnableSearch;
    public static MovieClient getInstance(){
        if(instance==null){
            instance = new MovieClient();
        }
        return instance;
    }
    public MovieClient() {
        moviesTrendingList = new MutableLiveData<>();
        moviesPopularList = new MutableLiveData<>();
        movieSearchList = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMoviesPopular(){
        return moviesPopularList;
    }

    public LiveData<List<MovieModel>> getMovieSearchList() {
        return movieSearchList;
    }

    public LiveData<List<MovieModel>> getMoviesTrendingList() {
        return moviesTrendingList;
    }

    public void searchPopularMovies(int page){
        if(retrofitMoviesRunnable!= null){
            retrofitMoviesRunnable = null;
        }
        retrofitMoviesRunnable = new RetrofitMoviesRunnable(page);
        final Future handler = AppExecutors.getInstance().networkIo().submit(retrofitMoviesRunnable);
        AppExecutors.getInstance().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
                Log.v("TAG","I canceled");
            }
        },5000, TimeUnit.MILLISECONDS);
    }

    public void searchTrendingMovies(int page){
        if(retrofitMoviesNewRunnable!= null){
            retrofitMoviesNewRunnable = null;
        }
        retrofitMoviesNewRunnable = new RetrofitMoviesNewRunnable(page);
        final Future handler = AppExecutors.getInstance().networkIo().submit(retrofitMoviesNewRunnable);
        AppExecutors.getInstance().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
                Log.v("TAG","I canceled");
            }
        },5000, TimeUnit.MILLISECONDS);
    }

    public void searchMovieApi(String query,int page) {

        if(retrieveMoviesRunnableSearch!= null){
            retrieveMoviesRunnableSearch = null;
        }

        retrieveMoviesRunnableSearch = new RetrieveMoviesRunnableSearch(query,page);

        final Future myHandler = AppExecutors.getInstance().networkIo().submit(retrieveMoviesRunnableSearch);

        AppExecutors.getInstance().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
//               cancelling the retroifit call so the app does not crash
                Log.v("TAG","canceled by me");
                myHandler.cancel(true);
            }
        }, 10000, TimeUnit.MILLISECONDS);
    }

    public class RetrofitMoviesRunnable implements Runnable{
        private int page;

        public RetrofitMoviesRunnable(int page) {
            this.page = page;
        }
        @Override
        public void run() {
            try{
                Response response = getPopular(page).execute();
                if(response.isSuccessful()){
                    List<MovieModel> movieModelList = new ArrayList<>(((MovieResponse)response.body()).getMovieList());
                    if(page==1){
                        moviesPopularList.postValue(movieModelList);
                    }
                    else{
                        List<MovieModel> current = moviesPopularList.getValue();
                        current.addAll(movieModelList);
                        moviesPopularList.postValue(current);
                    }
                    Log.v("TAG","This is success");
                }
                else{
                    Log.v("TAG","This is the problem " +response.errorBody());
                }
            } catch (IOException e) {
                e.printStackTrace();
                moviesPopularList.postValue(null);
            }
        }

        private Call<MovieResponse> getPopular(int page){
            return SingeltonRetrofit.getMovieApi().getPopularMovies(Crediantials.API_KEY,page);
        }
    }

    public class RetrofitMoviesNewRunnable implements Runnable{
        private int page;

        public RetrofitMoviesNewRunnable(int page) {
            this.page = page;
        }
        @Override
        public void run() {
            try{
                Response response = getTrending(page).execute();
                if(response.isSuccessful()){
                    List<MovieModel> movieModelList = new ArrayList<>(((MovieResponse)response.body()).getMovieList());
                    if(page==1){
                        moviesTrendingList.postValue(movieModelList);
                    }
                    else{
                        List<MovieModel> current = moviesTrendingList.getValue();
                        current.addAll(movieModelList);
                        moviesTrendingList.postValue(current);
                    }
                    Log.v("TAG","This is success");
                }
                else{
                    Log.v("TAG","This is the problem " +response.errorBody());
                }
            } catch (IOException e) {
                e.printStackTrace();
                moviesTrendingList.postValue(null);
            }
        }

        private Call<MovieResponse> getTrending(int page){
            return SingeltonRetrofit.getMovieApi().getTrendingMovies(Crediantials.API_KEY,page);
        }
    }

    private class RetrieveMoviesRunnableSearch implements Runnable{
        //  we have 2 type of query
        private String query;
        private int page;

        public RetrieveMoviesRunnableSearch(String query, int page) {
            this.query = query;
            this.page = page;
        }

        @Override
        public void run() {
            try{
                Response response = getMovies(query,page).execute();

                if(response.isSuccessful()){
                    List<MovieModel> list = new ArrayList<>(((MovieResponse)response.body()).getMovieList());

                    if(page == 1){
//                        upgrading the values
//                        postvalue: used for background thread
//                        setvalue : not for background thread
                        movieSearchList.postValue(list);
                    }else{
                        List<MovieModel> current = movieSearchList.getValue();
                        current.addAll(list);
                        movieSearchList.postValue(current);
                    }
                }else{
                    Log.v("TAG",response.errorBody().string());
                    movieSearchList.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                movieSearchList.postValue(null);
            }
        }
        //       search method in runnable(so it happen background)
        private Call<MovieResponse> getMovies(String query,int page){
            return SingeltonRetrofit.getMovieApi().searchMovie(Crediantials.API_KEY,query,String.valueOf(page));
        }
    }

}