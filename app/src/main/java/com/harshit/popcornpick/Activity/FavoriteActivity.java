package com.harshit.popcornpick.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.harshit.popcornpick.Adapter.FavouriteAdapter;
import com.harshit.popcornpick.Adapter.OnMovieListener;
import com.harshit.popcornpick.EntityAndDb.Crediantials;
import com.harshit.popcornpick.EntityAndDb.Detail;
import com.harshit.popcornpick.EntityAndDb.FavDB;
import com.harshit.popcornpick.EntityAndDb.FavEntity;
import com.harshit.popcornpick.Helper.SingeltonRetrofit;
import com.harshit.popcornpick.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity implements OnMovieListener {

    RecyclerView recyclerView;
    ProgressBar progressBar;

    ImageView backBtn;

    List<Integer> list;
    FavouriteAdapter favouriteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        progressBar = findViewById(R.id.loadingFavouritePB);
        progressBar.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.favoriteRV);
        backBtn = findViewById(R.id.backfromFavourite);
        backBtn.setOnClickListener(v->finish());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<FavEntity> favEntities = FavDB.getInstance(getApplicationContext()).getFavDao().getAll();
        for(FavEntity fav:favEntities){
            list.add(fav.getMovie_id());
        }

//        This list is for fetching the ids from the favourite (FIREBASE)
        List<Detail> favouriteList = new ArrayList<>();
        favouriteAdapter = new FavouriteAdapter(this,favouriteList,this);
        for(Integer integer :list){
            Call<Detail> call = SingeltonRetrofit.getMovieApi().getMovieById(integer, Crediantials.API_KEY);
            call.enqueue(new Callback<Detail>() {
                @Override
                public void onResponse(Call<Detail> call, Response<Detail> response) {
                    if(response.isSuccessful()){
                        Detail detail = response.body();
                        progressBar.setVisibility(View.GONE);
                        favouriteList.add(detail);
                        favouriteAdapter.setFavouritelist(favouriteList);
                    }
                }

                @Override
                public void onFailure(Call<Detail> call, Throwable t) {

                }
            });
        }
        recyclerView.setAdapter(favouriteAdapter);


    }

    @Override
    public void onMovieClick(int position, boolean adapter) {
        Intent i = new Intent(this,DetailActivity.class);
        Detail detail = favouriteAdapter.getClickedMovie(position);
        if(detail!=null){
            i.putExtra("movie",detail.getId());
            Log.v("TAG","The model is succes");
            startActivity(i);
        }
    }
}