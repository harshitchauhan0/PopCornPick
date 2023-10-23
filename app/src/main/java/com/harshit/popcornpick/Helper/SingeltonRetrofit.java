package com.harshit.popcornpick.Helper;

import com.harshit.popcornpick.EntityAndDb.Crediantials;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingeltonRetrofit {
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(Crediantials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static MovieApi movieApi = retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi(){
        return movieApi;
    }
}
