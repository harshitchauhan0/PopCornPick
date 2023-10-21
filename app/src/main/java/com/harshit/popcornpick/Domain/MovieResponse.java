package com.harshit.popcornpick.Domain;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.harshit.popcornpick.Domain.MovieModel;

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
