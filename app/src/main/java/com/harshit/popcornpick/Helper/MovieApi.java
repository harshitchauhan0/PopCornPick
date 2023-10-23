package com.harshit.popcornpick.Helper;

import com.harshit.popcornpick.EntityAndDb.Detail;
import com.harshit.popcornpick.EntityAndDb.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("/3/movie/top_rated")
    Call<MovieResponse> getPopularMovies(
        @Query("api_key") String key,
        @Query("page")  int page
    );

    @GET("/3/movie/now_playing")
    Call<MovieResponse> getTrendingMovies(
            @Query("api_key") String key,
            @Query("page")  int page
    );

    @GET("/3/movie/{pathId}")
    Call<Detail> getMovieById(
        @Path("pathId") int pathID,
        @Query("api_key") String key
    );

    @GET("/3/search/movie")
    Call<MovieResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") String page
    );

}