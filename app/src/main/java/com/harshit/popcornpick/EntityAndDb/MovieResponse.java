package com.harshit.popcornpick.EntityAndDb;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieResponse {

    @SerializedName("results")
    @Expose
    private List<MovieModel> movieList;

    public List<MovieModel> getMovieList() {
        return movieList;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movieList=" + movieList +
                '}';
    }
}
